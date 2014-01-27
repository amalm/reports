package dynamicreports.reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class CustomerReport {

	public void report()
	{
		Connection connection = null;
		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(
	                    "jdbc:h2:../reports-base/target/jpa","", "");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
	 
		JasperReportBuilder report = DynamicReports.report();//a new report
		report
		  .columns(
		      Columns.column("Customer Id", "id", DataTypes.integerType()),
		      Columns.column("First Name", "firstName", DataTypes.stringType()),
		      Columns.column("Last Name", "lastName", DataTypes.stringType()),
		      Columns.column("Date", "birthDate", DataTypes.dateType()))
		  .title(//title of the report
		      Components.text("Customer report with Dynamic Reports")
			  .setHorizontalAlignment(HorizontalAlignment.CENTER))
			  .pageFooter(Components.pageXofY())//show page number on the page footer
			  .setDataSource("SELECT id, firstName, lastName, birthDate FROM customer", 
	                                  connection);
	 
		try {
			report.show();
			report.toPdf(new FileOutputStream("target/report.pdf"));
			report.toExcelApiXls(new FileOutputStream("target/report.xls"));
		} catch (DRException e) {
			throw new RuntimeException("Report generation exception", e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found", e);
		}
	}
}
