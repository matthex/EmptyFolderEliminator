package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JList;

public class Gui {

	private JFrame frmEmptyFolderEliminator;
	private JScrollPane scrollPane;
	private JList list;
	private JFileChooser folderChooser = new JFileChooser();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frmEmptyFolderEliminator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEmptyFolderEliminator = new JFrame();
		frmEmptyFolderEliminator.setTitle("Empty Folder Eliminator");
		frmEmptyFolderEliminator.setBounds(100, 100, 400, 550);
		frmEmptyFolderEliminator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEmptyFolderEliminator.getContentPane().setLayout(new MigLayout("", "[grow]", "[][][][grow]"));
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderChooser.setAcceptAllFileFilterUsed(false);
		

		JMenuBar menuBar = new JMenuBar();
		frmEmptyFolderEliminator.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenFile = new JMenuItem("Choose Folder");
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFolder();
			}
		});
		mnFile.add(mntmOpenFile);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmClose);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmInfo = new JMenuItem("Info");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmEmptyFolderEliminator,
					    "<html><b>Empty Folder Eliminator</b> is developed by Matthias Koch in 2012.<br><br>This little program is intended to be a helper for<br>finding and deleting empty folders.<br>If you have any problems or questions feel free<br> to contact me: <a href=\"mailto:matthex.koch@googlemail.com\">matthex.koch@googlemail.com</a>.",
					    "Information",
					    JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(mntmInfo);
		frmEmptyFolderEliminator.getContentPane().setLayout(new MigLayout("", "[grow]", "[][][][grow]"));
		
		JButton btnOpenLogFile = new JButton("Choose Folder");
		btnOpenLogFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFolder();
			}
		});
		
		JLabel lblChooseAFolder = new JLabel("<html>Choose a folder which should be deleted if it is empty.<br>All empty subfolders will be deleted, too.</html>");
		frmEmptyFolderEliminator.getContentPane().add(lblChooseAFolder, "cell 0 0");
		frmEmptyFolderEliminator.getContentPane().add(btnOpenLogFile, "cell 0 1");
		
		JLabel lblDeletedFolders = new JLabel("Deleted Folders:");
		frmEmptyFolderEliminator.getContentPane().add(lblDeletedFolders, "cell 0 2");
		
		scrollPane = new JScrollPane();
		frmEmptyFolderEliminator.getContentPane().add(scrollPane, "cell 0 3,grow");
		
		list = new JList();
		scrollPane.setViewportView(list);
		
		list = new JList();
	}
	
	private void chooseFolder()
	{
		frmEmptyFolderEliminator.getContentPane().add(folderChooser);
		if (folderChooser.showOpenDialog(folderChooser) == JFileChooser.APPROVE_OPTION){
			File chosenFolder;
			if(folderChooser.getSelectedFile()==null)
			{
				chosenFolder = folderChooser.getCurrentDirectory();
			}
			else
			{
				chosenFolder = folderChooser.getSelectedFile();
			}

			int yesno = JOptionPane.showConfirmDialog(null,
					"All empty folders in\n"+chosenFolder+"\nwill be deleted.\nProceed?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
			if(yesno==0) 
				{
					displayFolders(core.EmptyFolderEliminator.eliminateEmptyFolder(chosenFolder));
				}
		}
	}
	
	private void displayFolders(List<String> deletedFolders)
	{
		if(deletedFolders.size()==0)
		{
			String[] noFolders = new String[1];
			noFolders[0] = "No empty folders found.";
			list = new JList(noFolders);
			scrollPane.setViewportView(list);
			return;
		}
		String[] folderStrings = new String[deletedFolders.size()];
		for(int i=0; i<deletedFolders.size(); i++)
		{
			folderStrings[i] = deletedFolders.get(i);
		}
		list = new JList(folderStrings);
		scrollPane.setViewportView(list);
	}

}
