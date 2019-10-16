/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;


import Databases.DBConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author DG sahib
 */
public class CalendarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private GridPane calendarGrid;
    @FXML
    private ComboBox yearComboBox;
    @FXML
    private ToggleGroup calendarViewGroup;
    @FXML
    private RadioButton byMonth;
    @FXML
    private RadioButton byWeek;
    @FXML
    private ComboBox monthComboBox;
    String calendarType;
    int month;
    int totalDays;
    int week;
    ObservableList <String> Month;
    ObservableList <String> Year;
    int firstDay;
    int YEAR;
    String[] days;
    String[] months;
    @FXML
    private Label yearError;
    @FXML
    private Label viewError;
    @FXML
    private Label titleLabel;
    @FXML
    private Button nextBtn;
    @FXML
    private Button prebtn;
    Statement s;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        days=new String[20];
        days[0]="Sun";
        days[1]="Mon";
        days[2]="Tue";
        days[3]="Wed";
        days[4]="Thu";
        days[5]="Fri";
        days[6]="Sat";
        month=1;
        totalDays=0;
        week=0;
        Year=FXCollections.observableArrayList();
        Year.addAll("2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030");
        yearComboBox.setItems(Year);
        months=new String[20];
        months[0]="January";
        months[1]="February";
        months[2]="March";
        months[3]="April";
        months[4]="May";
        months[5]="June";
        months[6]="July";
        months[7]="August";
        months[8]="September";
        months[9]="October";
        months[10]="November";
        months[11]="December";
        prebtn.setDisable(true);
    }    
    
    @FXML
    public void search()
    {
        if(!yearComboBox.getSelectionModel().isEmpty())
        {
            String selectedYear=yearComboBox.getSelectionModel().getSelectedItem().toString();
            RadioButton selectedRadioButton = (RadioButton) calendarViewGroup.getSelectedToggle();
            String view = selectedRadioButton.getText();
            
            YEAR=Integer.parseInt(selectedYear);
            month=1;
            nextBtn.setDisable(false);
            
            
            if(view.contains("By Month"))
            {
                calendarType="By Month";
                displayCalendar();
            }
            else if(view.contains("By Week"))
            {
                calendarType="By Week";
                displayByWeek();
            }
            
        }
        else
        {
            if(yearComboBox.getSelectionModel().isEmpty())
                yearError.setText("select year from list");
            if(!calendarViewGroup.getSelectedToggle().isSelected())
                viewError.setText("select View Type");
        }
    } 
    public  int calculateDays(int year)
    {
        for( int i=2000;i<year;i++)
        {
            if (isLeapYear(i))
                    totalDays += 366;
            else
                    totalDays += 365;
        }
        
        
        return totalDays;
    }
    
    public void getFirstDay(int totalDays)
    {
        int l = (totalDays % 7);
	if (l <= 6)
            firstDay = l;
    }
    
    public void displayCalendar()
    {
        
         int [] reservedDays=new int[31];
        int count=0;
        String m;
        if(month<10)
            m="0"+Integer.toString(month);
        else 
            m=Integer.toString(month);
        String yearMonth=yearComboBox.getSelectionModel().getSelectedItem().toString()+"-"+m+"-";
        try
        {
            s=DBConnection.conn.createStatement();
            String query="select start from appointment where start like '"+yearMonth+"%'";
            ResultSet r=s.executeQuery(query);
            while(r.next())
            {
                String result=r.getString(1);
                String []ResultParts=result.split(" ");
                String []dateParts=ResultParts[0].split("-");
                
                reservedDays[count]=Integer.parseInt(dateParts[2]);
                count++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        totalDays=0;
        firstDay=-1;
        int td=calculateDays(YEAR);
        int mdays=0;
        for(int i=1;i<month;i++)
        {
            mdays+=calculateDaysInMonth(i);
        }
        getFirstDay(td+mdays);
        
        calendarGrid.getChildren().clear();
        calendarGrid.add(new Label(days[6]), 0, 0);
        calendarGrid.add(new Label(days[0]), 1, 0);
        calendarGrid.add(new Label(days[1]), 2, 0);
        calendarGrid.add(new Label(days[2]), 3, 0);
        calendarGrid.add(new Label(days[3]), 4, 0);
        calendarGrid.add(new Label(days[4]), 5, 0);
        calendarGrid.add(new Label(days[5]), 6, 0);
        
        
        titleLabel.setText(months[month-1]+" "+YEAR);
        int row=1;
        int col=0;
        int counter=0;
        for (int i = 0; i < firstDay; i++) {
            calendarGrid.add(new Label (" "), col, row);
            counter+=1;
            col++; 
        }
        
        for (int i = 1; i <= calculateDaysInMonth(month); i++)
        {
            Button d=new Button(Integer.toString(i));
            d.setMinSize(50, 50);
            //d.setDisable(true);
            for(int k=0;k<reservedDays.length;k++)
            {
                if(reservedDays[k]==i)
                {
                    d.setStyle("-fx-background-color:green");
                    //d.setDisable(false);
                }
            }
            calendarGrid.add(d, col, row);
            col+=1;
            counter+=1;
            if (counter==7)
            {
                col=0;
                row+=1;
                counter=0;
            }
        }
    }
    
    public int calculateDaysInMonth(int m)
    {
        int daysInMonth=0;
        switch(m)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
               daysInMonth= 31;
               break;
            case 4:
            case 6:
            case 9:
            case 11:
               daysInMonth= 30;
               break;
            case 2:
                 if(isLeapYear(YEAR))
                     daysInMonth=29;
                 else
                     daysInMonth=28;
                 
                break;

                
                
         
        }
        return daysInMonth;
    }
    public boolean isLeapYear(int year)
    {
        if(((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0 && (year % 100) == 0))
            return true;
        else 
            return false;
    }

    @FXML
    public void next()
    {
        if(calendarType.contains("By Month"))
        {  month++;

            displayCalendar();
            if(month>=2)
                prebtn.setDisable(false);
            if(month<12)
                nextBtn.setDisable(false);
            if(month==12)
                nextBtn.setDisable(true);
            if(month==1)
                prebtn.setDisable(true);
        }
        else if(calendarType.contains("By Week"))
        {
            System.out.println("inside");
            displayByWeek();
        }
    }
    
    @FXML
    public void previous()
    {
        if(calendarType.contains("By Month"))
        {
            month--;
       
            displayCalendar();
            if(month>=2)
                prebtn.setDisable(false);
            if(month<12)
                nextBtn.setDisable(false);
            if(month==12)
                nextBtn.setDisable(true);
            if(month==1)
                prebtn.setDisable(true);
                 
        }
        else if(calendarType.contains("By Week"))
        {
            
        }
    }
    
    
    //by week
    int lastDate;
    String lastDay;
    int weekMonth;
    boolean flag=false;
    
    public void displayByWeek()
    {
        int [] reservedDays=new int[31];
        int count=0;
        
        String yearMonth=yearComboBox.getSelectionModel().getSelectedItem().toString()+"-"+month;
        try
        {
            s=DBConnection.conn.createStatement();
            String query="select start from appointment where start like '"+yearMonth+"%'";
            ResultSet r=s.executeQuery(query);
            while(r.next())
            {
                String result=r.getString(1);
                String []ResultParts=result.split(" ");
                String []dateParts=ResultParts[0].split("-");
                
                reservedDays[count]=Integer.parseInt(dateParts[2]);
                count++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(flag)
        {
            totalDays=0;
            firstDay=-1;
            int td=calculateDays(YEAR);
            int mdays=0;

            for(int i=1;i<month;i++)
            {
                mdays+=calculateDaysInMonth(i);
            }
            getFirstDay(td+mdays);
        }
        calendarGrid.getChildren().clear();
        calendarGrid.add(new Label(days[6]), 0, 0);
        calendarGrid.add(new Label(days[0]), 1, 0);
        calendarGrid.add(new Label(days[1]), 2, 0);
        calendarGrid.add(new Label(days[2]), 3, 0);
        calendarGrid.add(new Label(days[3]), 4, 0);
        calendarGrid.add(new Label(days[4]), 5, 0);
        calendarGrid.add(new Label(days[5]), 6, 0);
        int row=1;
        int col=0;
        int counter=0;
        for (int i = 0; i < firstDay; i++) {
            calendarGrid.add(new Label (" "), col, row);
            counter+=1;
            col++; 
        }
        
        //System.out.println(l);
        for (int i = counter; i < 7; i++)
        {
            Button d=new Button(Integer.toString(i));
            d.setMinSize(50, 50);
//            for(int k=0;k<reservedDays.length;k++)
//            {
//                if(reservedDays[k]==i)
//                    d.setStyle("background-color:blue");
//                    d.setStyle("-fx-background-color=black");
//                    System.out.println();
//            }
            calendarGrid.add(d, col, row);
            col+=1;
            counter+=1;
            if (counter==7)
            {
                col=0;
                
                counter=0;
            }
            lastDate=i;
        }
    }
}    