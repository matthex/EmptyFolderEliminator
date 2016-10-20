package core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EmptyFolderEliminator {
	
	public static List<String> eliminateEmptyFolder(File folder)
	{
		List<String> deletedFolders = new ArrayList<String>();
		
		//Check, whether we really are in a directory
		if(!folder.isDirectory())
		{
			return deletedFolders;
		}

		File[] subdirectories = folder.listFiles();
		
		//Recursion for all subfolders
		for (int i=0; i<subdirectories.length; i++)
		{
			if(subdirectories[i].isDirectory())
			{
				deletedFolders.addAll(eliminateEmptyFolder(subdirectories[i]));
			}
		}
		
		//Delete folder, if empty
		if(folder.delete())
		{
			deletedFolders.add(folder.getAbsolutePath());
		}
		return deletedFolders;
	}

}
