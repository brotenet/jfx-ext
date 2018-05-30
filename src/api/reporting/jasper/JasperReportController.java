package api.reporting.jasper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import com.lowagie.text.pdf.codec.Base64;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.ExcelDataSource;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.data.JRHibernateAbstractDataSource;
import net.sf.jasperreports.engine.data.JRHibernateIterateDataSource;
import net.sf.jasperreports.engine.data.JRHibernateListDataSource;
import net.sf.jasperreports.engine.data.JRHibernateScrollDataSource;
import net.sf.jasperreports.engine.data.JRJpaDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.HtmlResourceHandler;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOdsExporterConfiguration;
import net.sf.jasperreports.export.SimpleOdtExporterConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleRtfExporterConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

public class JasperReportController {

	private JasperReport jasper_report;
	private JasperPrint jasper_print;
	public static enum DataSourceTypes {NONE, JDBC, CSV};
	
	/**
	 * Loads the report data using a programmatically defined data-source.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param data_source - Jasper report data-source object
	 * @throws JRException
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, JRResultSetDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	/**
	 * Loads the report data using a programmatically defined data-source.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param data_source - Jasper report data-source object
	 * @throws JRException
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, JRCsvDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	/**
	 * Loads the report data using a programmatically defined data-source.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param data_source - Jasper report data-source object
	 * @throws JRException
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, ExcelDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	public void load(String jrxml_resource_path, Map<String, Object> parameters, JRXmlDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	/**
	 * Loads the report data using a programmatically defined data-source.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param data_source - Jasper report data-source object
	 * @throws JRException
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, JRJpaDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	/**
	 * Loads the report data using a programmatically defined data-source.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param data_source - Jasper report data-source object
	 * @throws JRException
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, JRHibernateAbstractDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	/**
	 * Loads the report data using a programmatically defined data-source.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param data_source - Jasper report data-source object
	 * @throws JRException
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, JRHibernateIterateDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	/**
	 * Loads the report data using a programmatically defined data-source.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param data_source - Jasper report data-source object
	 * @throws JRException
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, JRHibernateListDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	/**
	 * Loads the report data using a programmatically defined data-source.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param data_source - Jasper report data-source object
	 * @throws JRException
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, JRHibernateScrollDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	/**
	 * Loads the report data using a programmatically defined data-source.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param data_source - Jasper report data-source object
	 * @throws JRException
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, JsonDataSource data_source) throws JRException {
		jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
		jasper_print = JasperFillManager.fillReport(jasper_report, parameters, data_source);
	}
	
	/**
	 * Loads the report data using by instantiating a data-source on execution.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param datasource_type - Data-source type identifier using DataSourceTypes
	 * @param properties - Data-source properties defined in a key-value pair map
	 * @throws Exception
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, DataSourceTypes datasource_type, Properties properties) throws Exception{
		if(datasource_type == DataSourceTypes.CSV) {
			JRCsvDataSource datasource = new JRCsvDataSource(new File(String.valueOf(properties.getProperty("file"))));
			if(properties.containsKey("first_row_as_header")) {
				datasource.setUseFirstRowAsHeader(Boolean.valueOf(properties.getProperty("first_row_as_header")));
			}else {
				datasource.setUseFirstRowAsHeader(true);
			}
			if(properties.containsKey("record_delimiter")) {
				datasource.setRecordDelimiter(String.valueOf(properties.getProperty("record_delimiter")));
			}
			if(properties.containsKey("field_delimiter")) {
				datasource.setFieldDelimiter(String.valueOf(properties.getProperty("field_delimiter")).charAt(0));
			}
			if(properties.containsKey("date_pattern")) {
				datasource.setDatePattern(String.valueOf(properties.getProperty("date_pattern")));
			}
			if(properties.containsKey("time_zone_id")) {
				datasource.setTimeZone(String.valueOf(properties.getProperty("time_zone_id")));
			}
			if(properties.containsKey("number_pattern")) {
				datasource.setNumberPattern(String.valueOf(properties.getProperty("number_pattern")));
			}
			if(properties.containsKey("locale")) {
				datasource.setLocale(String.valueOf(properties.getProperty("locale")));
			}			
			jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
			jasper_print = JasperFillManager.fillReport(jasper_report, parameters, datasource);
		} else if(datasource_type == DataSourceTypes.JDBC) {
			Connection connection = DriverManager.getConnection(String.valueOf(properties.getProperty("url")), String.valueOf(properties.getProperty("user")), String.valueOf(properties.getProperty("password")));
			ResultSet result_set = connection.createStatement().executeQuery(String.valueOf(properties.getProperty("sql")));
			JRResultSetDataSource datasource = new JRResultSetDataSource(result_set);
			jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
			jasper_print = JasperFillManager.fillReport(jasper_report, parameters, datasource);
		}else {
			jasper_report = JasperCompileManager.compileReport(getClass().getResourceAsStream(jrxml_resource_path));
			jasper_print = JasperFillManager.fillReport(jasper_report, parameters, new JREmptyDataSource());
		}		
	}
	
	/**
	 * Loads the report data using by instantiating a data-source on execution.
	 * @param jrxml_resource_path - Package resource path to the .jrxml report file
	 * @param parameters - Report input parameters
	 * @param datasource_type - Data-source type identifier using DataSourceTypes
	 * @param properties - Data-source properties defined in a key-value pair string defined as {KEY=VALUE},{KEY=VALUE}... (e.g. {jdbc_driver=org.h2.Driver},{user=someuser}.. )
	 * @throws Exception
	 */
	public void load(String jrxml_resource_path, Map<String, Object> parameters, DataSourceTypes data_source_type, String properties) throws Exception{
		load(jrxml_resource_path, parameters, data_source_type, getProperties(properties, "},{"));
	}
	
	private Properties getProperties(String properties_string, String entrySeparator) throws IOException {
	    Properties properties = new Properties();
	    properties.load(new StringReader(properties_string.trim().substring(0, properties_string.length()-1).substring(1).replace(entrySeparator, "\n")));
	    return properties;
	}
	
	/**
	 * Get the amount of pages for a loaded report
	 * @return
	 */
	public int getPageCount() {
		return jasper_print.getPages().size();
	}
	
	/**
	 * Get a report page at a specific index and zoom level
	 * @param index - page index
	 * @param zoom - zoom factor
	 * @return
	 */
	public Image getPageImage(int index, double zoom) {
		try {
			return SwingFXUtils.toFXImage((BufferedImage) JasperPrintManager.printPageToImage(jasper_print,index-1,new Float(zoom)), null);
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Export RTF Document
	 * @param title - title text of save document file dialog
	 * @param extension_label - description name of file type of the document
	 * @param extension - file extension
	 * @param owner - owner window
	 * @throws IOException
	 * @throws JRException
	 */
	public void exportRTF(String title, String extension_label, String extension, Window owner) throws IOException, JRException {
		File file = File.createTempFile(UUID.randomUUID() + "-" + UUID.randomUUID(), ".rtf");
		JRRtfExporter exporter = new JRRtfExporter();
		SimpleRtfExporterConfiguration configuration = new SimpleRtfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasper_print));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(file.getAbsolutePath()));
		exporter.exportReport();
		saveReport(file, title, extension_label, extension, owner);
	}
	
	/**
	 * Export CSV Document
	 * @param title - title text of save document file dialog
	 * @param extension_label - description name of file type of the document
	 * @param extension - file extension
	 * @param owner - owner window
	 * @throws IOException
	 * @throws JRException
	 */
	public void exportCSV(String title, String extension_label, String extension, Window owner) throws IOException, JRException {
		File file = File.createTempFile(UUID.randomUUID() + "-" + UUID.randomUUID(), ".csv");
		JRCsvExporter exporter = new JRCsvExporter();
		SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasper_print));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(file.getAbsolutePath()));
		exporter.exportReport();
		saveReport(file, title, extension_label, extension, owner);
	}
	
	/**
	 * Export HTML Document
	 * @param title - title text of save document file dialog
	 * @param extension_label - description name of file type of the document
	 * @param extension - file extension
	 * @param owner - owner window
	 * @throws IOException
	 * @throws JRException
	 */
	public void exportHTML(String title, String extension_label, String extension, Window owner) throws IOException, JRException {
		File file = File.createTempFile(UUID.randomUUID() + "-" + UUID.randomUUID(), ".html");
		HtmlExporter exporter = new HtmlExporter();
		SimpleHtmlExporterConfiguration configuration = new SimpleHtmlExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasper_print));
		SimpleHtmlExporterOutput exporter_output = new SimpleHtmlExporterOutput(file.getAbsolutePath());
		exporter.setExporterOutput(exporter_output);
		exporter_output.setImageHandler(new HtmlResourceHandler() {
			Map<String, String> images = new HashMap<>();
			@Override
            public void handleResource(String id, byte[] data) {
                System.err.println("id" + id);
                images.put(id, "data:image/jpg;base64," + Base64.encodeBytes(data));
            }

            @Override
            public String getResourcePath(String id) {
                return images.get(id);
            }
		});
		exporter.exportReport();
		saveReport(file, title, extension_label, extension, owner);
	}
	
	/**
	 * Export XLSx Document
	 * @param title - title text of save document file dialog
	 * @param extension_label - description name of file type of the document
	 * @param extension - file extension
	 * @param owner - owner window
	 * @throws IOException
	 * @throws JRException
	 */
	public void exportXLSx(String title, String extension_label, String extension, Window owner) throws IOException, JRException {
		File file = File.createTempFile(UUID.randomUUID() + "-" + UUID.randomUUID(), ".xlsx");
		JRXlsxExporter exporter = new JRXlsxExporter();
		SimpleXlsxExporterConfiguration configuration = new SimpleXlsxExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasper_print));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file.getAbsolutePath()));
		exporter.exportReport();
		saveReport(file, title, extension_label, extension, owner);
	}
	
	/**
	 * Export DOCx Document
	 * @param title - title text of save document file dialog
	 * @param extension_label - description name of file type of the document
	 * @param extension - file extension
	 * @param owner - owner window
	 * @throws IOException
	 * @throws JRException
	 */
	public void exportDOCx(String title, String extension_label, String extension, Window owner) throws IOException, JRException {
		File file = File.createTempFile(UUID.randomUUID() + "-" + UUID.randomUUID(), ".docx");
		JRDocxExporter exporter = new JRDocxExporter();
		SimpleDocxExporterConfiguration configuration = new SimpleDocxExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasper_print));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file.getAbsolutePath()));
		exporter.exportReport();
		saveReport(file, title, extension_label, extension, owner);
	}
	
	/**
	 * Export ODS Document
	 * @param title - title text of save document file dialog
	 * @param extension_label - description name of file type of the document
	 * @param extension - file extension
	 * @param owner - owner window
	 * @throws IOException
	 * @throws JRException
	 */
	public void exportODS(String title, String extension_label, String extension, Window owner) throws IOException, JRException {
		File file = File.createTempFile(UUID.randomUUID() + "-" + UUID.randomUUID(), ".ods");
		JROdsExporter exporter = new JROdsExporter();
		SimpleOdsExporterConfiguration configuration = new SimpleOdsExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasper_print));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file.getAbsolutePath()));
		exporter.exportReport();
		saveReport(file, title, extension_label, extension, owner);
	}
	
	/**
	 * Export ODT Document
	 * @param title - title text of save document file dialog
	 * @param extension_label - description name of file type of the document
	 * @param extension - file extension
	 * @param owner - owner window
	 * @throws IOException
	 * @throws JRException
	 */
	public void exportODT(String title, String extension_label, String extension, Window owner) throws IOException, JRException {
		File file = File.createTempFile(UUID.randomUUID() + "-" + UUID.randomUUID(), ".odt");
		JROdtExporter exporter = new JROdtExporter();
		SimpleOdtExporterConfiguration configuration = new SimpleOdtExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasper_print));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file.getAbsolutePath()));
		exporter.exportReport();
		saveReport(file, title, extension_label, extension, owner);
	}
	
	/**
	 * Export PDF Document
	 * @param title - title text of save document file dialog
	 * @param extension_label - description name of file type of the document
	 * @param extension - file extension
	 * @param owner - owner window
	 * @throws IOException
	 * @throws JRException
	 */
	public void exportPDF(String title, String extension_label, String extension, Window owner) throws IOException, JRException {
		File file = File.createTempFile(UUID.randomUUID() + "-" + UUID.randomUUID(), ".pdf");
		JRPdfExporter exporter = new JRPdfExporter();
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasper_print));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file.getAbsolutePath()));
		exporter.exportReport();
		saveReport(file, title, extension_label, extension, owner);
	}
	
	private void saveReport(File report, String title, String extension_label, String extension, Window owner) throws IOException {
		FileChooser dialog = new FileChooser();
		dialog.getExtensionFilters().add(new ExtensionFilter(extension_label, new String[] { "*" + extension }));
		dialog.setTitle(title);
		dialog.setInitialFileName("report");
		String destination = null;
		try {destination = dialog.showSaveDialog(owner).getAbsolutePath();}catch(Exception ignore){};
		if(destination != null) {
			if (!destination.endsWith(extension) || !extension.endsWith(extension.toUpperCase())) {
				destination = destination + extension;
			}		
			Files.copy(Paths.get(report.getAbsolutePath()), Paths.get(destination),StandardCopyOption.REPLACE_EXISTING);
		}		
	}
	
	/**
	 * Display an exception dialog by consuming a Throwable object
	 * @param exception
	 * @param title
	 * @param text
	 */
	public void showExceptionDialog(Throwable exception, String title, String text) {
		StringWriter string_writer = new StringWriter();
		PrintWriter print_writer = new PrintWriter(string_writer);
		exception.printStackTrace(print_writer);
		String exception_details = string_writer.toString();
		Alert dialog = new Alert(AlertType.ERROR);
		dialog.setTitle(title);
		dialog.setHeaderText(text);
		Label label = new Label("The exception stacktrace was:");
		label.setPrefWidth(600);
		TextArea textArea = new TextArea(exception_details);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		dialog.getDialogPane().setContent(expContent);
		dialog.getDialogPane().setMinSize(650, 500);
		dialog.showAndWait();
	}
}
