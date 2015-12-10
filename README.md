# G52SAD-Coursework2
G52SAD Coursework2



Project Name - GV PhotoViewer

Introduction - A small program made to browse and organise images in a folder. The program also provides basic image enhancement features. 

Java Classes - 	DirectoryDisplayController.java,
				SceneManager.java,
				ListFilesUtil.java,
				ImageViewApplication.java,
				ImageViewController.java
FXML 		 -	ImageView.fxml,
				DirectoryDisplay.fxml

ImageViewApplication - the main java class to intialize and start the program

Controller files and FXML files corresponds with each other to provide the visual design and all the methods

ImageViewController 	   <-> ImageView.fxml   - produces the image window
DirectoryDisplayController <-> DirectoryDisplay - produces the file browser window

ListFilesUtil.java - returns the list of all the subdirectory and files in a directory

SceneManager.java  - works as a singleton classes to join all the controller with their fxml to make all the methods accessible by different classes




