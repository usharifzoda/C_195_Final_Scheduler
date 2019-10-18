/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;


import Databases.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DG sahib
 */
public class CalendarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    int M;
    
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
    @FXML
    private ComboBox selectMonth;
    
    
    Statement s;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        selectMonth.setVisible(false);
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
        
        selectMonth.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");
        
    
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
            
            nextBtn.setDisable(false);
            
            
            if(view.contains("By Month"))
            {
                month=1;
                M=month;
                selectMonth.setVisible(false);
                calendarType="By Month";
                displayCalendar();
            }
            else if(view.contains("By Week"))
            {
                selectMonth.setVisible(true);
                calendarType="By Week";
               // displayBox();
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
    @FXML
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
        //getFirstDay(td+mdays);
        System.out.println(td+mdays);
        totalDays=0;
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
        System.out.println(firstDay);
        for (int i = 0; i < firstDay; i++) {
            calendarGrid.add(new Label (" "), col, row);
            counter+=1;
            col++; 
        }
        
        for (int i = 1; i <= calculateDaysInMonth(month); i++)
        {
            Button d=new Button(Integer.toString(i));
            d.setMinSize(50, 50);
           

            for(int k=0;k<reservedDays.length;k++)
            {
                if(reservedDays[k]==i)
                {
                    d.setStyle("-fx-background-color:green");
                    
                    d.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) 
                        {
                            
                            DisplayAppointments(event);
                        }
                    });
                   
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
        {   month++;
            M=month;
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
            curWeek=curWeek+1;
            if(curWeek==2)
                prebtn.setDisable(false);
            
            displayWeek(1,0,0,LastDate+1);
           
        }
    }
    
    @FXML
    public void previous()
    {
        if(calendarType.contains("By Month"))
        {
            month--;
            M=month;
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
            curWeek-=1;
            displayWeek(1,0,0,LastDate-13);
        }
    }
    
    @FXML
    public void displayBox()
    {
        titleLabel.setText(" ");
        displayByWeek();
    }
    //by week
    int LastDate;
    int curWeek=1;
    
    
    public void displayByWeek()
    {
        curWeek=1;
        totalDays=0;
        int td=calculateDays(YEAR);
        int mdays=0;
        int index=selectMonth.getSelectionModel().getSelectedIndex();
        for(int i=1;i<index+1;i++)
        {
            mdays+=calculateDaysInMonth(i);
        }
        System.out.println(index+1);
        System.out.println(td+mdays);
        getFirstDay(td+mdays);
        
        int row=1;
        int col=0;
        int counter=0;
        System.out.println(firstDay);
        for (int i = 0; i < firstDay; i++) {
            calendarGrid.add(new Label (" "), col, row);
            counter+=1;
            col++; 
            if (counter==7)
            {
                col=0;
                row+=1;
                counter=0;
            }
        }
        displayWeek(row,col,counter,1);
       
        
        
    }
    @FXML
    public void clearCalendar()
    {
        titleLabel.setText(" ");
        month=1;
        selectMonth.getSelectionModel().clearSelection();
        selectMonth.setVisible(false);
        calendarGrid.getChildren().clear();
        titleLabel.setText(" ");
        nextBtn.setDisable(true);
        prebtn.setDisable(true);
        if(byMonth.isSelected())
            byMonth.setSelected(false);
        if(byWeek.isSelected())
            byWeek.setSelected(false);
        
    }
    public void displayWeek(int row,int col, int counter,int count)
    {
        int [] reservedDays=new int[31];
        int c=0;
        String m;
        int month=selectMonth.getSelectionModel().getSelectedIndex()+1;
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
                
                reservedDays[c]=Integer.parseInt(dateParts[2]);
                c++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        calendarGrid.getChildren().clear();
        calendarGrid.add(new Label(days[6]), 0, 0);
        calendarGrid.add(new Label(days[0]), 1, 0);
        calendarGrid.add(new Label(days[1]), 2, 0);
        calendarGrid.add(new Label(days[2]), 3, 0);
        calendarGrid.add(new Label(days[3]), 4, 0);
        calendarGrid.add(new Label(days[4]), 5, 0);
        calendarGrid.add(new Label(days[5]), 6, 0);
        int index=selectMonth.getSelectionModel().getSelectedIndex();
        M=index+1;
        titleLabel.setText(months[index]+" "+YEAR+"   Week "+curWeek);
       
        boolean startWeekEnded=false;
        for (int i = counter; i < 7; i++)
        {
            if(count<=0)
            {
                Label l=new Label(" ");
                calendarGrid.add(l, col, row);
                startWeekEnded=true;
            }
            else
            {
                Button d=new Button(Integer.toString(count));
                d.setMinSize(50, 50);
                System.out.println(count);
                for(int k=0;k<reservedDays.length;k++)
                {
                    if(reservedDays[k]==count)
                    {
                        d.setStyle("-fx-background-color:green");

                        d.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) 
                            {
                                System.out.println("handler set");

                                DisplayAppointments(event);
                            }
                        });

                    }
                }
                calendarGrid.add(d, col, row);
            }
            
           
            
            col+=1;
            counter+=1;
            if (counter==7)
            {
                col=0;
                row+=1;
                counter=0;
            }
            
            count++;
            if(count>calculateDaysInMonth(selectMonth.getSelectionModel().getSelectedIndex()+1))
            {
                nextBtn.setDisable(true);
                break;
            }
        }
        if(startWeekEnded)
            prebtn.setDisable(true);
        LastDate=count-1;
        
        if(curWeek==2)
            prebtn.setDisable(false);
   }
    public void DisplayAppointments(ActionEvent e)
    {
        String day="";
        
        String dayName=e.getSource().toString();
        System.out.println(dayName);
        for(int i=0;i<dayName.length();i++)
        {
            if(dayName.charAt(i)=='\'')
            {
                for(int j=i+1;j<dayName.length();j++)
                {
                    if(dayName.charAt(j)=='\'')
                        break;
                    else
                        day+=dayName.charAt(j);
                }
                break;
            }
            else continue;
        }
        if(Integer.parseInt(day)<10)
            day="0"+Integer.parseInt(day);
        
            
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXMLViews/calendarAppointmentView.fxml"));
            Parent calendarAppointmentView = loader.load();
            
            Scene editAppointmentScene = new Scene(calendarAppointmentView);
            CalendarAppointmentViewController controller = loader.getController();
            
            controller.initCalendarAppointmentView(YEAR,M, day);
            
            Stage window = new Stage();//(Stage) ((Node) e.getSource()).getScene().getWindow();
            window.setScene(editAppointmentScene);
            window.showAndWait();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        
        
    }
    
    
    @FXML
    public void back(ActionEvent event)throws IOException
    {
        
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/MainView.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
}    