package api.reporting.jasper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import api.commons.WindowManager;
import api.reporting.jasper.JasperReportController.DataSourceTypes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class JasperReportView extends AnchorPane {
	
	//TODO: add these to Github doc 
	//{jdbc_driver=org.h2.Driver},{user=user},{password=password},{url=jdbc:h2:/opt/GIT/RMCRM/database;AUTO_SERVER=TRUE},{sql=SELECT ID, STATUS, REGION, COUNTRY, CURRENT_INSTALLATION, SHORTNAME, LONGNAME, VERSION FROM PUBLIC.CUSTOMERS;}
	//{jdbc_driver=org.postgresql.Driver},{user=postgres},{password=rootpswd},{url=jdbc:postgresql://localhost:5432/postgres},{sql=SELECT ID, STATUS, REGION, COUNTRY, CURRENT_INSTALLATION, SHORTNAME, LONGNAME, "VERSION" FROM public.customers;}
	
	JasperReportController jasper_controller;
	private String jrxmlResourcePath;
	private Double zoom_default = 2.0;
	private Double zoom = 2.0;
	private DataSourceTypes dataSourceType = DataSourceTypes.NONE;
	private String dataSourceProperties = "";
	
	@FXML private Label page;
	@FXML private Spinner<Integer> page_select;
	@FXML private ScrollPane container;
	
	public JasperReportView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JasperReportView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		init();
	}

	private void init() {
		page_select.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
	}
	
	/**
	 * Get the resource path of the .jrxml report file
	 * @return
	 */
	public String getJrxmlResourcePath() {
		return jrxmlResourcePath;
	}

	/**
	 * Set the the .jrxml report resource file path
	 * @param jrxmlResourcePath
	 */
	public void setJrxmlResourcePath(String jrxmlResourcePath) {
		this.jrxmlResourcePath = jrxmlResourcePath;
	}
	
	/**
	 * Get the type of data-source. Only applies if a data-source is not object is not define in load() method.
	 * @return
	 */
	public DataSourceTypes getDataSourceType() {
		return dataSourceType;
	}

	/**
	 * Set the type of data-source. Only applies if a data-source is not object is not define in load() method.
	 * @param dataSourceType
	 */
	public void setDataSourceType(DataSourceTypes dataSourceType) {
		this.dataSourceType = dataSourceType;
	}
	
	/**
	 * Get the data-source properties
	 * @return
	 */
	public String getDataSourceProperties() {
		return dataSourceProperties;
	}

	/**
	 * Set the data-source properties
	 * @param dataSourceProperties
	 */
	public void setDataSourceProperties(String dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}

	/**
	 * Get the set zoom level
	 * @return
	 */
	public Double getZoom() {
		return zoom;
	}
	
	/**
	 * Get the default zoom level used for zoom-reset
	 * @return
	 */
	public Double getZoomDefault() {
		return zoom_default;
	}

	/**
	 * Set the default zoom level used in zoom-reset
	 * @param zoom_default
	 */
	public void setZoomDefault(Double zoom_default) {
		this.zoom_default = zoom_default;
	}

	/**
	 * Set the set zoom level
	 * @param zoom
	 */
	public void setZoom(Double zoom) {
		this.zoom = zoom;
	}

	/**
	 * Zoom out on report page using custom value 
	 * @param value
	 */
	public void zoomOut(Double value) {
		zoom = zoom - value;
		Image image = jasper_controller.getPageImage(page_select.getValue(), getZoom());
		page.setGraphic(new ImageView(image));
		page.autosize();
		page.translateXProperty().bind(container.widthProperty().subtract(page.widthProperty()).divide(2));
	}
	
	/**
	 * Zoom out on report page using 0.2 double value
	 */
	public void zoomOut() {
		zoomOut(0.2);
	}
	
	/**
	 * Reset zoom to 1.0 double value
	 */
	public void zoomReset() {
		zoom = zoom_default;
		Image image = jasper_controller.getPageImage(page_select.getValue(), getZoom());
		page.setGraphic(new ImageView(image));
		page.autosize();
		page.translateXProperty().bind(container.widthProperty().subtract(page.widthProperty()).divide(2));
	}
	
	/**
	 * Zoom in on report page using custom value
	 * @param value
	 */
	public void zoomIn(Double value) {
		zoom = zoom + value;
		Image image = jasper_controller.getPageImage(page_select.getValue(), getZoom());
		page.setGraphic(new ImageView(image));
		page.autosize();
		page.translateXProperty().bind(container.widthProperty().subtract(page.widthProperty()).divide(2));
	}
	
	/**
	 * Zoom in on report page using 0.2 double value
	 */
	public void zoomIn() {
		zoomIn(0.2);
	}
	
	/**
	 * Export CSV Document
	 */
	public void exportCSV() {
		try {
			if(jasper_controller != null) {
				jasper_controller.exportCSV("Export CSV file", "CSV", ".csv", this.getScene().getWindow());
			}			
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Export Errors", "There was an error during export.");
		}
	}
	
	/**
	 * Export XLSx Document
	 */
	public void exportXLSx() {
		try {
			if(jasper_controller != null) {
				jasper_controller.exportXLSx("Export XLSx spreadsheet", "XLSx", ".xlsx", this.getScene().getWindow());
			}			
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Export Errors", "There was an error during export.");
		}
	}
	
	/**
	 * Export ODS Document
	 */
	public void exportODS() {
		try {
			if(jasper_controller != null) {
				jasper_controller.exportODS("Export ODS spreadsheet", "ODS", ".ods", this.getScene().getWindow());
			}			
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Export Errors", "There was an error during export.");
		}
	}
	
	/**
	 * Export HTML Document
	 */
	public void exportHTML() {
		try {
			if(jasper_controller != null) {
				jasper_controller.exportHTML("Export HTML page", "HTML", ".html", this.getScene().getWindow());
			}			
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Export Errors", "There was an error during export.");
		}
	}
	
	/**
	 * Export RTF Document
	 */
	public void exportRTF() {
		try {
			if(jasper_controller != null) {
				jasper_controller.exportRTF("Export RTF document", "RTF", ".rtf", this.getScene().getWindow());
			}			
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Export Errors", "There was an error during export.");
		}
	}
	
	/**
	 * Export DOCx Document
	 */
	public void exportDOCx() {
		try {
			if(jasper_controller != null) {
				jasper_controller.exportDOCx("Export DOCx document", "DOCx", ".docx", this.getScene().getWindow());
			}			
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Export Errors", "There was an error during export.");
		}
	}
	
	/**
	 * Export ODT Document
	 */
	public void exportODT() {
		try {
			if(jasper_controller != null) {
				jasper_controller.exportODT("Export ODT document", "ODT", ".odt", this.getScene().getWindow());
			}			
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Export Errors", "There was an error during export.");
		}
	}
	
	/**
	 * Export PDF Document
	 */
	public void exportPDF(){
		try {
			if(jasper_controller != null) {
				jasper_controller.exportPDF("Export PDF document", "PDF", ".pdf", this.getScene().getWindow());
			}
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Export Errors", "There was an error during export.");
		}
	}
	
	/**
	 * Update image graphics for selected page
	 */
	public void updatePage() {
		Image image = jasper_controller.getPageImage(page_select.getValue(), getZoom());
		page.setGraphic(new ImageView(image));
		page.autosize();
		page.translateXProperty().bind(container.widthProperty().subtract(page.widthProperty()).divide(2));
	}
	
	/**
	 * Load report using values defined in DataSourceType and DataSourceProperties and report parameters
	 * @param parameters
	 */
	public void load(Map<String, Object> parameters) {		
		try {
			jasper_controller = new JasperReportController();
			jasper_controller.load(jrxmlResourcePath, parameters, dataSourceType, dataSourceProperties);
			Image image = jasper_controller.getPageImage(1, zoom);
			page.setGraphic(new ImageView(image));
			page.autosize();
			page.translateXProperty().bind(container.widthProperty().subtract(page.widthProperty()).divide(2));
			WindowManager.bind(page_select, 1, jasper_controller.getPageCount(), 1, 1);
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Loading Errors", "There was an error while loading the report.");
		}
	}
	
	/**
	 * Load report using values defined in DataSourceType and DataSourceProperties
	 */
	public void load() {
		load(new HashMap<>());
	}
	
	/**
	 *  Load report a custom-defined report-controller. Usually used when instantiating a custom data-source type that is not an empty, CSV or JDBC data-source
	 * @param parameters
	 * @param controller
	 */
	public void load(JasperReportController controller) {		
		try {
			jasper_controller = controller;
			Image image = jasper_controller.getPageImage(1, zoom);
			page.setGraphic(new ImageView(image));
			page.autosize();
			page.translateXProperty().bind(container.widthProperty().subtract(page.widthProperty()).divide(2));
			WindowManager.bind(page_select, 1, jasper_controller.getPageCount(), 1, 1);
		} catch (Exception exception) {
			jasper_controller.showExceptionDialog(exception, "Loading Errors", "There was an error while loading the report.");
		}
	}
}
