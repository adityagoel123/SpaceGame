package com.aditya.xlabs.utilities.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aditya.xlabs.utilities.IConvertorService;

public class ConvertorServiceImpl implements IConvertorService {

	public List<String> convertShotsToIndexes(List<String> shots) {
		if(null != shots && !(shots.isEmpty())){
			List<String> listOfIndexes = new ArrayList<String>();
			for(int i=0;i<shots.size();i++){
				listOfIndexes.add(shots.get(i).replace("x",""));
			}
			return listOfIndexes;
		} else 
			return Collections.emptyList();
	}
}

