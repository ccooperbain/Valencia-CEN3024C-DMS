import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
/**
 * Name: Christopher Bain
 * Course: 202620-CEN-3024C-23585
 * Date: 04/04/2026
 * Class Name: MainWindow.java
 *
 * This class represents the GUI and functionality for the form of java swing
 *It handles what the Main.java used to handel
 *
 */

import javax.swing.JSpinner;


public class DatabaseGui extends JFrame {
    TvShowManager tvShowManager = new TvShowManager();
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JTextField titleEntryField;
    SpinnerNumberModel monthModel = new SpinnerNumberModel(1,1,12,1);
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
    DefaultTableModel dbTableModel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {return false;}
    };
    private JTable tvShowTable;

    DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID","Title","Animated","Month","Date","Year","Rating","Is On Watchlist"}, 0){
        @Override
        public boolean isCellEditable(int row, int column) {return false;}
    };
    private DefaultTableModel tvShowModel = tableModel;
    private JScrollPane scrollPane;
    private JLabel averageResult;
    private int selectedEntry = -1;


    /**
     * Constructor that contains the functionality of the buttons inside the program.
     */
    public DatabaseGui(){
        monthEntry.setModel(monthModel);
        dateEntry.setModel(dateModel);
        yearEntry.setModel(yearModel);
        addShowButton1.setEnabled(false);
        removeEntryButton.setEnabled(false);
        updateButton.setEnabled(false);
        calculateAverageButton.setEnabled(false);
        tvShowTable.setCellSelectionEnabled(false);
        tvShowTable.setRowSelectionAllowed(true);


        addShowButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    tvShowManager.addShowToDatabase(titleEntryField.getText(),isAnimated.isSelected(), (Integer) monthEntry.getValue(),(Integer)dateEntry.getValue(),(Integer)yearEntry.getValue(),ratingEntrySlider.getValue()/10,onWatchList.isSelected());
                    refreshTable();
                }catch(Exception ee){
                    //Display error message or window
                    System.out.println("An error occured on the add show1 button");
                    System.out.println(ee.getMessage());
                }
            }
        });

        calculateAverageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    averageResult.setText("AVERAGE RATING: "+String.format("%.2f",tvShowManager.calculateAvgRatingDatabase()));
                }catch(Exception ee){
                    System.out.println("Error in calculate average button");
                }
            }
        });

        addShowFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Button clicked  & searching for the file
                JFileChooser fileChooser = new JFileChooser();
                //fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)","txt"));
                fileChooser.setFileFilter(new FileNameExtensionFilter("SQL lite (*.db)","db"));
                String filePath;

                //if file is found
                int chooserResult = fileChooser.showOpenDialog(mainPanel);
                if(chooserResult == JFileChooser.APPROVE_OPTION){
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    JOptionPane.showMessageDialog(mainPanel,"Selected: " + filePath);
                    tvShowManager.setUrl(filePath);
                }else{return;}

                //https://www.c-sharpcorner.com/blogs/retrieving-and-displaying-database-records-in-java-using-jtable#:~:text=Set%20up%20your%20environment:%20Ensure,data%20in%20a%20Swing%20application.

                //try to connect to the specified database from the file and add the value of the connection to the variable  gui connection
                ResultSet fullTable = tvShowManager.loadDatabase(filePath);
                if(fullTable == null){
                    System.out.println("No database found");
                    return;
                }

                try{
                        ResultSetMetaData metaData = fullTable.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        System.out.println("the column n count is " + columnCount);

                        dbTableModel.setRowCount(0);
                        dbTableModel.setColumnCount(0);

                        for(int i=1; i<= columnCount; i++){
                            dbTableModel.addColumn(metaData.getColumnName(i));
                            System.out.println(metaData.getColumnName(i) + " " + i);
                        }
                        tvShowTable.setModel(dbTableModel);

                        refreshTable();

                        addShowButton1.setEnabled(true);
                        removeEntryButton.setEnabled(true);
                        updateButton.setEnabled(true);
                        calculateAverageButton.setEnabled(true);
                        addShowFromFileButton.setEnabled(false);

                }catch(SQLException es){
                    System.out.println();
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
                tvShowManager.deleteShowFromDatabase(tvShowId);
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

                String animatedValue = tvShowTable.getValueAt(selectedEntry,2).toString();
                isAnimated.setSelected(animatedValue.equalsIgnoreCase("Yes"));


                monthEntry.setValue(tvShowTable.getValueAt(selectedEntry,3));
                dateEntry.setValue(tvShowTable.getValueAt(selectedEntry,4));
                yearEntry.setValue(tvShowTable.getValueAt(selectedEntry,5));

                double doubleSliderValue = (Double) tvShowTable.getValueAt(selectedEntry,6)*10;
                int intSliderValue = (int) doubleSliderValue;
                ratingEntrySlider.setValue(intSliderValue);

                String watchValue = tvShowTable.getValueAt(selectedEntry, 7).toString();
                onWatchList.setSelected(watchValue.equalsIgnoreCase("Yes"));

            }

        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedEntry = tvShowTable.getSelectedRow();
                if(selectedEntry == -1){
                    JOptionPane.showMessageDialog(mainPanel,"Please select an entry");
                    return;
                }
                System.out.println("the stored selected entry has a value of " + tvShowTable.getValueAt(selectedEntry,1));
                tvShowManager.updateDatabaseShowEntry((int)tvShowTable.getValueAt(selectedEntry,0),titleEntryField.getText(),isAnimated.isSelected(),(int) monthEntry.getValue(),(int)dateEntry.getValue(),(int) yearEntry.getValue(),ratingEntrySlider.getValue()/10,onWatchList.isSelected());

                refreshTable();
            }
        });
    }

    /**
     * Method Name: main
     *
     * Purpose:
     * Serves as the entry point of the program. It initializes the TvShowManager,
     * and displays the gui of the application.
     *
     *Arguments:
     *
     * Return Value:
     *
     */

    public static void main(String[] args){

        JFrame frame = new JFrame("TvShow Manager");
        DatabaseGui window = new DatabaseGui();
        frame.setContentPane(window.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //window.refreshTable();

    }

    /**
     * Method Name: refreshTable
     *
     * Reloads the table showing in the application with the most up to date information
     * that the array list contains
     *
     * arguments:
     *
     * Return Value:
     */
    private void refreshTable(){

        //TODO come back to check if this works.
        dbTableModel.setRowCount(0);
        dbTableModel.setColumnCount(0);

        try{

            if(tvShowManager.getConnection() == null){
                System.out.println("No database connection");
                return;
            }

            Statement statement = tvShowManager.getConnection().createStatement();
            ResultSet allEntries = statement.executeQuery("SELECT * FROM tv_shows");
            ResultSetMetaData metaData = allEntries.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println("the column count is " + columnCount);



            for(int i=1; i<= columnCount; i++){
                dbTableModel.addColumn(metaData.getColumnName(i));
                System.out.println(metaData.getColumnName(i) + " " + i);
            }
            tvShowTable.setModel(dbTableModel);

            while(allEntries.next()){
                Object[] row = new Object[columnCount];
                for(int i=1;i<=columnCount;i++){

                    Object value = allEntries.getObject(i);
                    if(metaData.getColumnName(i).equals("animated") || metaData.getColumnName(i).equals("watchlist")){
                        int intValue = (int) value;
                        if(intValue == 1){row[i-1] = "yes";}else{row[i-1] = "no";}
                    }else{
                        row[i-1] = value;
                    }
                }
                dbTableModel.addRow(row);
            }

            addShowButton1.setEnabled(true);
            removeEntryButton.setEnabled(true);
            updateButton.setEnabled(true);
            calculateAverageButton.setEnabled(true);
        }catch(SQLException es){
            System.out.println("Error in refresh function");
        }

    }

}
