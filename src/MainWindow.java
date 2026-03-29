import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSpinner;

//TODO merge the month day and year in the colum
public class MainWindow extends JFrame {
    TvShowManager tvShowManager = new TvShowManager();
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JTextField titleEntryField;
    SpinnerNumberModel monthModel = new SpinnerNumberModel(1,1,31,1);
    private JSpinner monthEntry;
    SpinnerNumberModel dateModel = new SpinnerNumberModel(1,1,31,1);
    private JSpinner dateEntry;
    SpinnerNumberModel yearModel = new SpinnerNumberModel(2000,1928,3005,1);
    private JSpinner yearEntry;

    private JSlider ratingEntrySlider;
    private JCheckBox isAnimated;
    private JCheckBox onWatchList;
    private JPanel topCtrPanel;
    private JPanel botCtrPanel;
    private JButton removeEntryButton;
    private JButton updateButton;
    private JButton calculateAverageButton;
    private JPanel radioButtonPanel;
    private JPanel watchListButtonPanel;
    private JButton addShowButton1;
    private JButton addShowFromFileButton;
    private JTable tvShowTable;
    DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID","Title","Animated","Month","Date","Year","Rating","Is On Watchlist"}, 0){
        @Override
        public boolean isCellEditable(int row, int column) {return false;}
    };
    //private DefaultTableModel tvShowModel = (DefaultTableModel) tvShowTable.getModel();
    private DefaultTableModel tvShowModel = tableModel;
    private JScrollPane scrollPane;
    private JLabel averageResult;
    private JLabel testValue;
    private int selectedEntry = -1;


    public MainWindow(){
        //addColumns();
        monthEntry.setModel(monthModel);
        dateEntry.setModel(dateModel);
        yearEntry.setModel(yearModel);
        //tvShowTable.setModel(tableModel);


        addShowButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    TvShow entry = new TvShow(tvShowManager.setId(),titleEntryField.getText(),isAnimated.isSelected(), (Integer) monthEntry.getValue(),(Integer)dateEntry.getValue(),(Integer)yearEntry.getValue(),ratingEntrySlider.getValue()/10,onWatchList.isSelected());
                    tvShowManager.addShow(entry);
                    refreshTable();
                    tvShowManager.saveToFile();
                }catch(Exception ee){
                    //Display error message or window
                    //TODO error message for incorrect entries
                }
            }
        });
        calculateAverageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    averageResult.setText("RATING AVERAGE: " + String.format("%.2f",tvShowManager.calculateAverageRating()));
            }
        });
        addShowFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int chooserResult = fileChooser.showOpenDialog(mainPanel);
                if(chooserResult == JFileChooser.APPROVE_OPTION){
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    JOptionPane.showMessageDialog(mainPanel,"Selected: " + filePath);
                    tvShowManager.loadFromFileGui(filePath);
                    refreshTable();
                    tvShowManager.saveToFile();
                }
            }
        });
        removeEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedEntry = tvShowTable.getSelectedRow();
                if(selectedEntry == -1){
                    JOptionPane.showMessageDialog(mainPanel,"Please select an entry");
                    return;
                }
                int tvShowId = (int) tvShowTable.getValueAt(selectedEntry,0);
                tvShowManager.deleteShowGui(tvShowId);

                refreshTable();
            }
        });

        tvShowTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                int selectedEntry = tvShowTable.getSelectedRow();
                if(selectedEntry == -1){
                    return;
                }
                titleEntryField.setText(tvShowTable.getValueAt(selectedEntry,1).toString());

                if((boolean)tvShowTable.getValueAt(selectedEntry,2)){
                    isAnimated.setSelected(true);
                    System.out.println("is animated is equal to true");
                }else{isAnimated.setSelected(false);
                    System.out.println("the animated is equal to false");
                }

                monthEntry.setValue(tvShowTable.getValueAt(selectedEntry,3));
                dateEntry.setValue(tvShowTable.getValueAt(selectedEntry,4));
                yearEntry.setValue(tvShowTable.getValueAt(selectedEntry,5));

                double doubleSliderValue = (Double) tvShowTable.getValueAt(selectedEntry,6)*10;
                int intSliderValue = (int) doubleSliderValue;
                ratingEntrySlider.setValue(intSliderValue);

                if((boolean)tvShowTable.getValueAt(selectedEntry,7)){
                    onWatchList.setSelected(true);
                    System.out.println("is watchlist is equal to true");
                }else{onWatchList.setSelected(false);
                    System.out.println("is watchlist is equal to false");}

            }

        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedEntry = tvShowTable.getSelectedRow();

                System.out.println("the stored selected entry has a value of " + tvShowTable.getValueAt(selectedEntry,1));

                if(selectedEntry == -1){
                    JOptionPane.showMessageDialog(mainPanel,"Please select an entry");
                    return;
                }
                for(TvShow tvShow : tvShowManager.getTvShows()){
                    if(tvShow.getId() == (Integer)tvShowTable.getValueAt(selectedEntry,0)){
                        tvShow.setTitle(titleEntryField.getText());
                        tvShow.setCompletionMonth((int)monthEntry.getValue());
                        tvShow.setCompletionDate((int)dateEntry.getValue());
                        tvShow.setCompletionYear((int)yearEntry.getValue());

                        tvShow.setRating(ratingEntrySlider.getValue()/10);
                        tvShow.setAnimatedGui(isAnimated.isSelected());
                        tvShow.setOnWatchListGui(onWatchList.isSelected());


                    }
                }
                refreshTable();
            }
        });
    }

    public static void main(String[] args){

        JFrame frame = new JFrame("TvShow Manager");
        MainWindow window = new MainWindow();
        frame.setContentPane(window.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        window.refreshTable();


        /*JFrame frame = new JFrame("TV Show Manager");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        frame.setVisible(true);*/


    }

    private void refreshTable(){

        tvShowModel.setRowCount(0);

        for(TvShow show : tvShowManager.getTvShows()){
            tvShowModel.addRow(new Object[]{
                    show.getId(),
                    show.getTitle(),
                    show.isAnimated(),
                    show.getCompletionMonth(),
                    show.getCompletionDate(),
                    show.getCompletionYear(),
                    show.getRating(),
                    show.isOnWatchList(),

            });
        }
        tvShowTable.setModel(tvShowModel);


    }


    /*private void addColumns(){
        tvShowModel.addColumn("ID");
        tvShowModel.addColumn("Title");
        tvShowModel.addColumn("Is Animated");
        tvShowModel.addColumn("Month Completed");
        tvShowModel.addColumn("Day Completed");
        tvShowModel.addColumn("Year Completed");
        tvShowModel.addColumn("Rating");
        tvShowModel.addColumn("Is On watchlist");
    }*/
}
