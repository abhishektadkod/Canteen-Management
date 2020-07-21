/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canteenmanagement;
import java.util.Scanner;
import java.sql.*;
import java.lang.*;



class canteenoperations{
    
    double dis=0,bill;
   
    
    void calculate_discount(double amm,int usn)
    {   
        int o_count=0;
         try{  
           
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/canteen","root","");  
            //here sonoo is database name, root is username and password  
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from student where SID="+usn);  
            while(rs.next())  
            o_count=rs.getInt(3);
             Statement stmt1=con.createStatement(); 
             if(o_count>3)
             {
            dis=4.5;
            stmt1.executeUpdate("UPDATE `student` SET `O_count`=0 WHERE SID="+usn);
             }
             else
             {
                 o_count=o_count+1;
                dis=0.0;
                stmt1.executeUpdate("UPDATE `student` SET `O_count`="+o_count+" WHERE SID="+usn);
             }
             
            con.close();  
            }catch(Exception e){ System.out.println(e);}  
        
       
    }
    
    int calculate_penalty()
    {
        int o_cancel=4,a;
        if(o_cancel>3) a=20;
        else a=0;
        return a;
    }
    
    void bill(int SID,int CNO,int item_id,int tra)
    {
        double amm;
        canteenoperations order=new canteenoperations(); 
        
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/canteen","root","");  
            //here sonoo is database name, root is username and password  
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from menu where Item_id="+item_id);  
            while(rs.next())  
            bill=(float)rs.getInt(3);
            con.close();  
            }catch(Exception e){ System.out.println(e);}  
            
            amm=bill;
            order.calculate_discount(bill, SID);
            bill=bill-4.5;
            tra++;
        
         try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/canteen","root","");  
            //here sonoo is database name, root is username and password  
            Statement stmt=con.createStatement();  
            stmt.executeUpdate("INSERT INTO `order_table`(`Tra_id`, `SID`, `CNO`, `Item_id`, `Price`) VALUES ("+tra+","+SID+","+CNO+","+item_id+","+bill+")");
            con.close();  
            }catch(Exception e){ System.out.println(e);}  
        
        System.out.println("Order Summary:-\n");
        
        try{  
                Class.forName("com.mysql.jdbc.Driver");  
                Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/canteen","root","");  
                //here sonoo is database name, root is username and password  
                Statement stmt=con.createStatement();  
                ResultSet rs=stmt.executeQuery("select * from menu where Item_id="+item_id );  
                System.out.println("NAME\tPRICE");  
                while(rs.next())  {
                System.out.println(rs.getString(2)+"\t"+rs.getString(3));  
                  }
                con.close();  
                }catch(Exception e){ System.out.println(e);}
        
        
        
        
        System.out.println("");
        System.out.print("Amount:"+amm);
        System.out.print("\tPenalty:"+0);
        System.out.print("\tDiscount:"+dis);
        System.out.println("\tTotal:"+bill);
//The final amount should include tax for total cancelations the user may have done along with the discounts
    System.out.println("Transaction Id="+tra);
    System.out.println("***PLEASE CARRY THE TRANSCATION ID FOR THE REFERENCE AT THE COUNTER");
    
    }
}

/**
 *
 * @author Abhishek
 */
public class CanteenManagement {

    /**
     * @param args the command line arguments
     */
    int flag=0;
    static int tra;
    
    CanteenManagement()
    {
        tra=1001;
    }
    
    void order(int USN)//This function has to be changed
    {
        int choice;
        Scanner S=new Scanner(System.in);
        canteenoperations bill=new canteenoperations(); 
        
        System.out.println("List of Canteens:");
        System.out.println("1.Shetty");
        System.out.println("2.Sadhguru");
        System.out.println("3.Suresh Anna");
        do{
        System.out.println("Choose a canteen:");
        choice=S.nextInt();
        switch(choice)
        {
            
            case 1: int id1=0;
                    try{  
                    Class.forName("com.mysql.jdbc.Driver");  
                    Connection con=DriverManager.getConnection(  
                    "jdbc:mysql://localhost:3306/canteen","root","");  
                    //here sonoo is database name, root is username and password  
                    Statement stmt=con.createStatement();  
                    ResultSet rs=stmt.executeQuery("select * from menu where CNO="+choice
                            );  
                    System.out.println("Id\tNAME\tPRICE");  
                    while(rs.next())  {
                    System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));  
                      }
                    con.close();  
                    }catch(Exception e){ System.out.println(e);} 
                     do{
                    if(flag>0)System.out.println("Invalid itemid.Enter the Item ID again");
                    else System.out.println("Enter the Item ID");
                    id1=S.nextInt();flag++;}while(id1<10 || id1>20);
                    tra++;
                    bill.bill(USN,choice,id1,tra);
                    break;
                    
            case 2:int id2=0;
                try{  
                Class.forName("com.mysql.jdbc.Driver");  
                Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/canteen","root","");  
                //here sonoo is database name, root is username and password  
                Statement stmt=con.createStatement();  
                ResultSet rs=stmt.executeQuery("select * from menu where CNO="+choice );  
                System.out.println("Id\tNAME\t\tPRICE");  
                while(rs.next())  {
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));  
                  }
                con.close();  
                }catch(Exception e){ System.out.println(e);} 
               do{
                  if(flag>0)System.out.println("Invalid itemid.Enter the Item ID again");
                  else System.out.println("Enter the Item ID");
                id2=S.nextInt();flag++;}while(id2<20 || id2>30);
               tra++;
                bill.bill(USN,choice,id2,tra);
                break;
                
            case 3:int id3=0;
                   try{  
                Class.forName("com.mysql.jdbc.Driver");  
                Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/canteen","root","");  
                //here sonoo is database name, root is username and password  
                Statement stmt=con.createStatement();  
                ResultSet rs=stmt.executeQuery("select * from menu where CNO="+choice
                        );  
                System.out.println("Id\tNAME\tPRICE");  
                while(rs.next())  {
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));  
                  }
                con.close();  
                }catch(Exception e){ System.out.println(e);} 
                do{
                if(flag>0)System.out.println("Invalid itemid.Enter the Item ID again");
                else System.out.println("Enter the Item ID");
                id3=S.nextInt();flag++;}while(id3<30 || id3>40);
                tra++;
                bill.bill(USN,choice,id3,tra);
                    break;
            case 4:System.out.println("EXIT");
                   System.exit(0);
            default:System.out.println("Invalid option");
        }
        }while(choice<5);
    }
    
   void user()
    {
      int flag=0;
      CanteenManagement session=new CanteenManagement();
      Scanner S=new Scanner(System.in);
       System.out.println("Enter USN");
       int USN=S.nextInt();
       try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/canteen","root","");  
            //here sonoo is database name, root is username and password  
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from student");  
            while(rs.next())  
            if(USN==rs.getInt(1))
            {
                flag=1;
                System.out.println("NAME:"+rs.getString(2));
            }
                con.close(); 
            }catch(Exception e){ System.out.println(e);}  
       //validate in the database
       if(flag==1)
           session.order(USN);
       else{
           System.out.println("Invalid USN");
           user();
       }
       
    }
   
    void canteen()
    {
      CanteenManagement session=new CanteenManagement();
      Scanner S=new Scanner(System.in);
      
        System.out.println("1.Shetty");
        System.out.println("2.Sadhguru");
        System.out.println("3.Suresh Anna");
       System.out.println("Enter canteen Number");
       int c=S.nextInt();
       int t=0;

       
       switch(c)
       {
           case 1:
                    try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/canteen","root","");  
            //here sonoo is database name, root is username and password  
            Statement stmt=con.createStatement();  
            
            ResultSet rs=stmt.executeQuery("SELECT o.OID,o.Tra_id,s.Name,m.Name,o.Price from order_table o,menu m,student s\n" +
"where o.item_id=m.item_id and s.SID=o.SID and o.CNO=1;");  
            System.out.println("OID \t Tra_ID \t Stud_name \t Item name \t Price");
            while(rs.next())  
            System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getInt(5));
             try{
                 System.out.println("Enter the tra_id of the order dispatched:");
                 t= S.nextInt();
                 
                String query = "delete from order_table where tra_id ="+t+";";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.execute();
                //stmt.executeUpdate("update table student s,order_table o set O_count=O_count"+1+"where s.SID=o.SID");
                //stmt=con.createStatement();  
            rs=stmt.executeQuery("select * from order_table where CNO=1;");
            System.out.println("OID \t Tra_ID \t Stud_ID \t CNO \t ItemID \t Price");
            while(rs.next())  
            System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5)+"\t"+rs.getInt(6));
             }
             
             catch(Exception e){ System.out.println(e);}
            con.close(); 
            }catch(Exception e){ System.out.println(e);}
             System.out.println("Oreder with transaction id "+t+" is processed");
              
                    break;
                   
           case 2:try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/canteen","root","");  
            //here sonoo is database name, root is username and password  
            Statement stmt=con.createStatement();  
            
            ResultSet rs=stmt.executeQuery("SELECT o.OID,o.Tra_id,s.Name,m.Name,o.Price from order_table o,menu m,student s\n" +
"where o.item_id=m.item_id and s.SID=o.SID and o.CNO=2;");  
            System.out.println("OID \t Tra_ID \t Stud_name \t Item name \t Price");
            while(rs.next())  
            System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t\t"+rs.getString(4)+"\t\t"+rs.getInt(5));
             try{
                 System.out.println("Enter the tra_id of the order dispatched:");
                 t= S.nextInt();
                 
                String query = "delete from order_table where tra_id ="+t+";";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.execute();
                //stmt.executeUpdate("update table student s,order_table o set O_count=O_count"+1+"where s.SID=o.SID");
                //stmt=con.createStatement();  
            
             }
             
             catch(Exception e){ System.out.println(e);}
            con.close(); 
            }catch(Exception e){ System.out.println(e);}
             System.out.println("Order with transaction id "+t+"is processed");
              
                    break;
                  
           case 3:try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/canteen","root","");  
            //here sonoo is database name, root is username and password  
            Statement stmt=con.createStatement();  
            
            ResultSet rs=stmt.executeQuery("SELECT o.OID,o.Tra_id,s.Name,m.Name,o.Price from order_table o,menu m,student s\n" +
"where o.item_id=m.item_id and s.SID=o.SID and o.CNO=3;");  
            System.out.println("OID \t Tra_ID \t Stud_name \t Item name \t Price");
            while(rs.next())  
            System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getInt(5));
             try{
                 System.out.println("Enter the tra_id of the order dispatched:");
                 t= S.nextInt();
                 
                String query = "delete from order_table where tra_id ="+t+";";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.execute();
                //stmt.executeUpdate("update table student s,order_table o set O_count=O_count"+1+"where s.SID=o.SID");
                //stmt=con.createStatement();  
            rs=stmt.executeQuery("select * from order_table where CNO=3;");
            System.out.println("OID \t Tra_ID \t Stud_ID \t CNO \t ItemID \t Price");
            while(rs.next())  
            System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5)+"\t"+rs.getInt(6));
             }
             
             catch(Exception e){ System.out.println(e);}
            con.close(); 
            }catch(Exception e){ System.out.println(e);}
             System.out.println("Oreder with transaction id "+t+"is processed");
              
                    break;
                  
           default: System.out.println("Invalid choice");
                    canteen();
       }
       
           
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
       
        CanteenManagement session=new CanteenManagement();
        int choice;
        Scanner S=new Scanner(System.in);
        
        do{
        System.out.println("Welcome to Canteen Management!");
        System.out.println("1)USER");//1]Order,2]Penalty?
        System.out.println("2)CANTEEN");//1]Queue,2]Statistics?
        System.out.println("Choose an option:");
        choice=S.nextInt();
        switch(choice)
        {
            case 1:session.user();
                    break;
            case 2:session.canteen();
                    break;
            case 99:System.out.println("Thank you for using our app");System.exit(0);
            default:System.out.println("Invalid option");
        }
        }while(true);
            }  
        }  
        
    
    
    


