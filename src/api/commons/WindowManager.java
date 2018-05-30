package api.commons;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Optional;

import api.commons.Environment.Resources;
import api.commons.Environment.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class WindowManager {
	
	//Collectors
	/**
	 * Get the parent window of the input node
	 * @param node
	 * @return
	 */
	public static Window windowOf(Node node) {
		return node.getScene().getWindow();
	}
	
	//Bindings
	/**
	 * Bind given TreeTableView to its data item class
	 * @param control
	 * @param bind_class
	 */
	public static void bind(TreeTableView<?> control, Class<?> bind_class) {
		for (int i = 0; i < bind_class.getDeclaredFields().length; i++) {
			control.getColumns().get(i).setCellValueFactory(new TreeItemPropertyValueFactory<>(bind_class.getDeclaredFields()[i].getName()));
		}
	}

	/**
	 * Set the min, max, step and selection values for a given Spinner
	 * @param control
	 * @param min
	 * @param max
	 * @param step
	 * @param selection
	 */
	public static void bind(Spinner<Integer> control, int min, int max, int step, int selection) {
		control.setValueFactory((SpinnerValueFactory<Integer>) new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, selection, step));
	}
	
	/**
	 * Set the min, max, step and selection values for a given Spinner
	 * @param control
	 * @param min
	 * @param max
	 * @param step
	 * @param selection
	 */
	public static void bind(Spinner<Double> control, double min, double max, double step, double selection) {
		control.setValueFactory((SpinnerValueFactory<Double>) new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, selection, step));
	}
	
	/**
	 * Set the options and selection value for a given Spinner
	 * @param control
	 * @param items
	 * @param selection
	 */
	public static void bind(Spinner<String> control, String[] items, String selection) {
		SpinnerValueFactory<String> factory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(FXCollections.observableArrayList(items));
		factory.setValue(selection);
		control.setValueFactory(factory);
	}
	
	//Parents
	/**
	 * Get a new Parent object for a given .fxml resource file.
	 * @param fxml_resource_path
	 * @return
	 * @throws IOException
	 */
	public static Parent get(String fxml_resource_path) throws IOException {
		return FXMLLoader.load(WindowManager.class.getResource(fxml_resource_path));
	}
	
	//Scenes
	/**
	 * Get a new Scene object for a given .fxml resource file and styled by a given .css resource file.
	 * @param fxml_resource_path
	 * @param css_resource_path
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Scene getScene(String fxml_resource_path, String css_resource_path, int width, int height) throws IOException {
		Scene scene;
		if((width > 0) & (height > 0)) {
			scene = new Scene(get(fxml_resource_path), width, height);
		}else {
			scene = new Scene(get(fxml_resource_path));
		}		
		if(css_resource_path != null) {
			scene.getStylesheets().add(WindowManager.class.getResource(css_resource_path).toExternalForm());
		}
		return scene;
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file and styled by a given .css resource file.
	 * @param fxml_resource_path
	 * @param css_resource_path
	 * @return
	 * @throws IOException
	 */
	public static Scene getScene(String fxml_resource_path, String css_resource_path) throws IOException {
		return getScene(fxml_resource_path, css_resource_path, -1, -1);
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file.
	 * @param fxml_resource_path
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Scene getScene(String fxml_resource_path, int width, int height) throws IOException {
		return getScene(fxml_resource_path, null, width, height);
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file.
	 * @param fxml_resource_path
	 * @return
	 * @throws IOException
	 */
	public static Scene getScene(String fxml_resource_path) throws IOException {
		return getScene(fxml_resource_path, null, -1, -1);
	}
	
	//Stages
	/**
	 *  Get a new Stage object for a given .fxml resource file and styled by a given .css resource file.
	 * @param fxml_resource_path
	 * @param css_resource_path
	 * @param style
	 * @param modality
	 * @param title
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Stage getStage(String fxml_resource_path, String css_resource_path, StageStyle style, Modality modality, String title, Image image, int width, int height) throws IOException {
		Stage stage;
		Scene scene;
		if((width > 0) & (height > 0)) {
			scene = new Scene(get(fxml_resource_path), width, height);
		}else {
			scene = new Scene(get(fxml_resource_path));
		}		
		if(css_resource_path != null) {
			scene.getStylesheets().add(WindowManager.class.getResource(css_resource_path).toExternalForm());
		}
		if(style != null) {
			stage = new Stage(style);
		}else {
			stage = new Stage(StageStyle.UTILITY);
		}
		stage.initOwner(new Stage());
		if(modality != null) {
			stage.initModality(modality);
		}else {
			stage.initModality(Modality.APPLICATION_MODAL);
		}
		stage.setScene(scene);
		if(title != null) {stage.setTitle(title);}
		if(image != null) {
			stage.getIcons().add(image);
		}else {
			stage.getIcons().add(Resources.getImage("null.png"));
		}
		return stage;
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file and styled by a given .css resource file.
	 * @param fxml_resource_path
	 * @param css_resource_path
	 * @param modality
	 * @param title
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Stage getStage(String fxml_resource_path, String css_resource_path, Modality modality, String title, Image image, int width, int height) throws IOException {
		return getStage(fxml_resource_path, css_resource_path, StageStyle.DECORATED, modality, title, image, width, height);
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file and styled by a given .css resource file.
	 * @param fxml_resource_path
	 * @param css_resource_path
	 * @param style
	 * @param title
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Stage getStage(String fxml_resource_path, String css_resource_path, StageStyle style, String title, Image image, int width, int height) throws IOException {
		return getStage(fxml_resource_path, css_resource_path, style, Modality.APPLICATION_MODAL, title, image, width, height);
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file and styled by a given .css resource file.
	 * @param fxml_resource_path
	 * @param css_resource_path
	 * @param title
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Stage getStage(String fxml_resource_path, String css_resource_path, String title, Image image, int width, int height) throws IOException {
		return getStage(fxml_resource_path, css_resource_path, StageStyle.DECORATED, Modality.APPLICATION_MODAL, title, image, width, height);
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file.
	 * @param fxml_resource_path
	 * @param style
	 * @param modality
	 * @param title
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Stage getStage(String fxml_resource_path, StageStyle style, Modality modality, String title, Image image, int width, int height) throws IOException {
		return getStage(fxml_resource_path, null, style, modality, title, image, width, height);
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file.
	 * @param fxml_resource_path
	 * @param modality
	 * @param title
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Stage getStage(String fxml_resource_path, Modality modality, String title, Image image, int width, int height) throws IOException {
		return getStage(fxml_resource_path, null, StageStyle.DECORATED, modality, title, image, width, height);
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file.
	 * @param fxml_resource_path
	 * @param style
	 * @param title
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Stage getStage(String fxml_resource_path, StageStyle style, String title, Image image, int width, int height) throws IOException {
		return getStage(fxml_resource_path, null, style, Modality.APPLICATION_MODAL, title, image, width, height);
	}
	
	/**
	 *  Get a new Scene object for a given .fxml resource file.
	 * @param fxml_resource_path
	 * @param title
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static Stage getStage(String fxml_resource_path, String title, Image image, int width, int height) throws IOException {
		return getStage(fxml_resource_path, null, StageStyle.DECORATED, Modality.APPLICATION_MODAL, title, image, width, height);
	}
	
	public static class Dialogs {
		
		public static enum FilesDialogTypes {SINGLE_FILE, MULTIPLE_FILES, DIRECTORY, SAVE_FILE};
		
		/**
		 * Show an exception dialog by consuming a Throwable object
		 * @param exception
		 * @param title
		 * @param text
		 * @param width
		 * @param height
		 */
		public static void showExceptionDialog(Throwable exception, String title, String text, int width, int height) {
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
			dialog.getDialogPane().setMinSize(width, height);
			dialog.showAndWait();
		}
		
		/**
		 * Show an exception dialog by consuming a Throwable object
		 * @param exception
		 * @param title
		 * @param text
		 */
		public static void showExceptionDialog(Throwable exception, String title, String text) {
			showExceptionDialog(exception, title, text, 600, 500);
		}
		
		/**
		 * Show a combo-box choice selection dialog
		 * @param title
		 * @param header_text
		 * @param content_text
		 * @param image
		 * @param choices
		 * @param default_choice
		 * @return
		 */
		public static String showChoiceDialog(String title, String header_text, String content_text, Image image, String[] choices, String default_choice) {
			ChoiceDialog<String> dialog = new ChoiceDialog<String>(default_choice, choices);
			if(title != null) {
				dialog.setTitle(title);
			}
			if(header_text != null) {
				dialog.setHeaderText(header_text);
			}
			if(content_text != null) {
				dialog.setContentText(content_text);
			}
			if(image != null) {
				dialog.setGraphic(new ImageView(image));
			}
			dialog.showAndWait();
			return dialog.getResult();
		}
		
		/**
		 * Show a combo-box choice selection dialog
		 * @param title
		 * @param header_text
		 * @param content_text
		 * @param image_resource_path
		 * @param choices
		 * @param default_choice
		 * @return
		 */
		public static String showChoiceDialog(String title, String header_text, String content_text, String image_resource_path, String[] choices, String default_choice) {
			return showChoiceDialog(title, header_text, content_text, Resources.getImage(image_resource_path), choices, default_choice);
		}
		
		/**
		 * Show a buttons based choice selection alert dialog
		 * @param alert_type
		 * @param title
		 * @param header_text
		 * @param content_text
		 * @param image
		 * @param choices
		 * @return
		 */
		public static String showAlertDialog(AlertType alert_type, String title, String header_text, String content_text, Image image, String[] choices) {
			Alert dialog = new Alert(alert_type);
			dialog.setTitle(title);
			dialog.setHeaderText(header_text);
			dialog.setContentText(content_text);
			dialog.setGraphic(new ImageView(image));
			if(choices != null) {
				for(String choice : choices) {
					dialog.getButtonTypes().add(new ButtonType(choice));
				}
			}
			Optional<ButtonType> output = dialog.showAndWait();
			return output.get().getText();
		}
		
		/**
		 * Show a notification alert dialog
		 * @param alert_type
		 * @param title
		 * @param header_text
		 * @param content_text
		 * @param image
		 * @return
		 */
		public static String showAlertDialog(AlertType alert_type, String title, String header_text, String content_text, Image image) {
			return showAlertDialog(alert_type, title, header_text, content_text, image, null);
		}
		
		/**
		 * Show a buttons based choice selection alert dialog
		 * @param alert_type
		 * @param title
		 * @param header_text
		 * @param content_text
		 * @param image_resource_path
		 * @param choices
		 * @return
		 */
		public static String showAlertDialog(AlertType alert_type, String title, String header_text, String content_text, String image_resource_path, String[] choices) {
			return showAlertDialog(alert_type, title, header_text, content_text, new Image(image_resource_path), choices);
		}
		
		/**
		 * Show a notification alert dialog
		 * @param alert_type
		 * @param title
		 * @param header_text
		 * @param content_text
		 * @param image_resource_path
		 * @return
		 */
		public static String showAlertDialog(AlertType alert_type, String title, String header_text, String content_text, String image_resource_path) {
			return showAlertDialog(alert_type, title, header_text, content_text, image_resource_path, null);
		}
		
		/**
		 * Show dialog for selecting files/directories for opening files or saving files
		 * @param title
		 * @param dialog_type
		 * @param extensions
		 * @param initial_directory
		 * @param default_file_name
		 * @param owner
		 * @param save_file
		 * @return
		 * @throws Exception
		 */
		public static File[] showFilesDialog(String title, FilesDialogTypes dialog_type, HashMap<String, String[]> extensions, File initial_directory, String default_file_name, Window owner, File save_file) throws Exception{
			File[] output = null;
			if(dialog_type == FilesDialogTypes.SINGLE_FILE || dialog_type == FilesDialogTypes.MULTIPLE_FILES || dialog_type == FilesDialogTypes.SAVE_FILE) {
				FileChooser dialog = new FileChooser();
				dialog.setTitle(title);
				dialog.setInitialFileName(default_file_name);
				if(initial_directory != null) {
					dialog.setInitialDirectory(initial_directory);
				}else {
					dialog.setInitialDirectory(new File(Session.UserHome()));
				}
				if(extensions != null) {
					for(String key : extensions.keySet()) {
						dialog.getExtensionFilters().add(new ExtensionFilter(key, extensions.get(key)));
					}
				}else {
					dialog.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
				}
				if(dialog_type == FilesDialogTypes.SINGLE_FILE) {
					File file = dialog.showOpenDialog(owner);
					if(file != null) {
						output = new File[] {file};
					}
				}else if(dialog_type == FilesDialogTypes.MULTIPLE_FILES){
					java.util.List<File> files = dialog.showOpenMultipleDialog(owner);
					if(files != null) {
						output = files.toArray(new File[files.size()]);
					}					
				}else if(dialog_type == FilesDialogTypes.SAVE_FILE) {
					if(save_file != null) {
						File file = dialog.showSaveDialog(owner);
						if(file != null) {
							Files.copy(Paths.get(save_file.getAbsolutePath()), Paths.get(file.getAbsolutePath()),StandardCopyOption.REPLACE_EXISTING);
						}						
					}else {
						throw new Exception("Null save_file(File) supplied");
					}
				}else {
					throw new Exception("Invalid dialog_type(FilesDialogTypes) specified");
				}
			}else if(dialog_type == FilesDialogTypes.DIRECTORY){
				DirectoryChooser dialog = new DirectoryChooser();
				dialog.setTitle(title);
				if(initial_directory != null) {
					dialog.setInitialDirectory(initial_directory);
				}else {
					dialog.setInitialDirectory(new File(Session.UserDirectory()));
				}
				output = new File[] {dialog.showDialog(owner)};
			}else {
				throw new Exception("Invalid dialog_type(FilesDialogTypes) specified");
			}
			return output;
		}
		
		/**
		 * Show dialog for selecting files/directories for opening files or saving files
		 * @param title
		 * @param dialog_type
		 * @param extensions
		 * @param initial_directory
		 * @param default_file_name
		 * @param owner
		 * @param save_file
		 * @return
		 * @throws Exception
		 */
		public static File[] showFilesDialog(String title, FilesDialogTypes dialog_type, String[] extensions, File initial_directory, String default_file_name, Window owner, File save_file) throws Exception{
			HashMap<String, String[]> extensions_map =  new HashMap<>();
			if(extensions != null) {
				for(String extension : extensions) {
					extensions_map.put(extension, new String[]{extension});
				}
			}
			return showFilesDialog(title, dialog_type, extensions_map, initial_directory, default_file_name, owner, save_file);
		}
		
		/**
		 * Show dialog for selecting files/directories for opening files or saving files
		 * @param title
		 * @param dialog_type
		 * @param extension_label
		 * @param extension
		 * @param initial_directory
		 * @param default_file_name
		 * @param owner
		 * @param save_file
		 * @return
		 * @throws Exception
		 */
		public static File[] showFilesDialog(String title, FilesDialogTypes dialog_type, String extension_label, String extension, File initial_directory, String default_file_name, Window owner, File save_file) throws Exception{
			HashMap<String, String[]> extensions_map =  new HashMap<>();
			extensions_map.put(extension_label, new String[]{extension});
			return showFilesDialog(title, dialog_type, extensions_map, initial_directory, default_file_name, owner, save_file);
		}
		
		/**
		 * Show dialog for selecting files/directories for opening files or saving files
		 * @param title
		 * @param dialog_type
		 * @param extension
		 * @param initial_directory
		 * @param default_file_name
		 * @param owner
		 * @param save_file
		 * @return
		 * @throws Exception
		 */
		public static File[] showFilesDialog(String title, FilesDialogTypes dialog_type, String extension, File initial_directory, String default_file_name, Window owner, File save_file) throws Exception{
			HashMap<String, String[]> extensions_map =  new HashMap<>();
			extensions_map.put(extension, new String[]{extension});
			return showFilesDialog(title, dialog_type, extensions_map, initial_directory, default_file_name, owner, save_file);
		}
		
	}
}
