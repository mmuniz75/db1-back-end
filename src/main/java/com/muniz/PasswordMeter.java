package com.muniz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class PasswordMeter {
	
	private static enum Score {
		NUMBEROFCHARACTERS,	
		UPPERCASELETTERS,	
		LOWERCASELETTERS,	
		NUMBERS,
		SYMBOLS,	
		MIDDLENUMBERSORSYMBOLS,	
		REQUIREMENTS,	
		LETTERSONLY,	
		NUMBERSONLY,	
		REPEATCHARACTERS,	
		CONSECUTIVEUPPERCASELETTERS,	
		CONSECUTIVELOWERCASE,	
		CONSECUTIVENUMBERS,	
		SEQUENTIALLETTERS,	
		SEQUENTIALNUMBERS,	
		SEQUENTIALSYMBOLS
	}
	
	
	private static final Map<Score, String> rates = new HashMap<>();
	static {
	    rates.put(Score.NUMBEROFCHARACTERS,"+(n*4)");	
		rates.put(Score.UPPERCASELETTERS,"+((len-n)*2)");	
		rates.put(Score.LOWERCASELETTERS,"+((len-n)*2)");	
		rates.put(Score.NUMBERS,"+(n*4)");
		rates.put(Score.SYMBOLS,"+(n*6)");	
		rates.put(Score.MIDDLENUMBERSORSYMBOLS,"+(n*2)");	
		rates.put(Score.REQUIREMENTS,"+(n*2)");	
		rates.put(Score.LETTERSONLY,"-n");	
		rates.put(Score.NUMBERSONLY,"-n");	
		rates.put(Score.REPEATCHARACTERS,"-");	
		rates.put(Score.CONSECUTIVEUPPERCASELETTERS,"-(n*2)");	
		rates.put(Score.CONSECUTIVELOWERCASE,"-(n*2)");	
		rates.put(Score.CONSECUTIVENUMBERS,"-(n*2)");	
		rates.put(Score.SEQUENTIALLETTERS,"-(n*3)");	
		rates.put(Score.SEQUENTIALNUMBERS,"-(n*3)");	
		rates.put(Score.SEQUENTIALSYMBOLS,"-(n*3)");
	}  
	
	public static int scorePassword(String password) {
		int score  = 0;
		
		List<String> passwordScores = buildCheckVector(password);
		
		if(passwordScores.size()>0) { 
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			
			for(String passordScore:passwordScores){
				Integer result = 0;
				try{
					result = (Integer)engine.eval(passordScore);
				}catch(Exception ex){ex.printStackTrace();}
				score+= result;
			}
		}	
					
		return score<100?score:100;
	}
	
	private static List<String> buildCheckVector(String password){
		String[] checkVector = new String[]{
												checkScore(password,Score.NUMBEROFCHARACTERS),
												checkScore(password,Score.UPPERCASELETTERS),
												checkScore(password,Score.LOWERCASELETTERS),
												checkScore(password,Score.NUMBERS),
												checkScore(password,Score.SYMBOLS),
												checkScore(password,Score.MIDDLENUMBERSORSYMBOLS),
												checkScore(password,Score.REQUIREMENTS),
												checkScore(password,Score.LETTERSONLY),
												checkScore(password,Score.NUMBERSONLY),
												checkScore(password,Score.REPEATCHARACTERS),
												checkScore(password,Score.CONSECUTIVEUPPERCASELETTERS),
												checkScore(password,Score.CONSECUTIVELOWERCASE),
												checkScore(password,Score.CONSECUTIVENUMBERS),
												checkScore(password,Score.SEQUENTIALLETTERS),
												checkScore(password,Score.SEQUENTIALNUMBERS),
												checkScore(password,Score.SEQUENTIALSYMBOLS)
											 };
		
		List<String> scores = new ArrayList<>(Arrays.asList(checkVector));
		scores.removeIf(item -> item.equals(""));
		
		return scores;
	}
	
	
	private static String checkScore(String password,Score scoreType){
		String rate = "";
		int occur = 0;
		
		switch(scoreType){
			case NUMBEROFCHARACTERS:
				occur = checkNumberOfCharacters(password);
				break;
			case UPPERCASELETTERS:
				occur = checkUppercaseLetters(password);
				break;
			case LOWERCASELETTERS:
				occur = checkLowercaseLetters(password);
				break;
			case NUMBERS:
				occur = checkNumbers(password);
				break;
			case SYMBOLS:
				occur = checkSymbols(password);
				break;
			case MIDDLENUMBERSORSYMBOLS:
				occur = checkMiddleNumbersOrSymbols(password);
				break;
			case REQUIREMENTS:
				occur = checkRequirements(password);
				break;
			case LETTERSONLY:
				occur = checkLettersOnly(password);
				break;
			case NUMBERSONLY:
				occur = checkNumbersOnly(password);
				break;
			case REPEATCHARACTERS:
				occur = checkRepeatCharacters(password);
				break;
			case CONSECUTIVEUPPERCASELETTERS:
				occur = checkConsecutiveUppercaseLetters(password);
				break;
			case CONSECUTIVELOWERCASE:
				occur = checkConsecutiveLowercaseLetters(password);
				break;
			case CONSECUTIVENUMBERS:
				occur = checkConsecutiveNumbers(password);
				break;
			case SEQUENTIALLETTERS:
				occur = checkSequentialLetters(password);
				break;
			case SEQUENTIALNUMBERS:
				occur = checkSequentialNumbers(password);
				break;
			case SEQUENTIALSYMBOLS:
				occur = checkSequentialSymbols(password);
				break;
		}
		
		if(occur > 0) {		
			rate = rates.get(scoreType);
			if(rate.indexOf("len")>0){
				int calc = password.length() - occur;
				rate = rate.replaceAll("len-n", Integer.toBinaryString(calc));
			}else
				rate = rate.replaceAll("n", Integer.toString(occur));
		}	
		
		return rate;
	}
	
	
	//Additions
	private static int checkNumberOfCharacters(String password){
		int occur = 1;
		return occur;
	}
	private static int checkUppercaseLetters(String password){
		int occur = 1;
		return occur;
	}
	private static int checkLowercaseLetters(String password){
		int occur = 1;
		return occur;
	}
	private static int checkNumbers(String password){
		int occur = 1;
		return occur;
	}
	private static int checkSymbols(String password){
		int occur = 1;
		return occur;
	}
	private static int checkMiddleNumbersOrSymbols(String password){
		int occur = 1;
		return occur;
	}
	private static int checkRequirements(String password){
		int occur = 1;
		return occur;
	}
	
	//Deductions
	private static int checkLettersOnly(String password){
		int occur = 1;
		return occur;
	}
	private static int checkNumbersOnly(String password){
		int occur = 1;
		return occur;
	}
	
	// source not avaliable
	private static int checkRepeatCharacters(String password){
		int occur = 0;
		return occur;
	}
	private static int checkConsecutiveUppercaseLetters(String password){
		int occur = 0;
		return occur;
	}
	private static int checkConsecutiveLowercaseLetters(String password){
		int occur = 0;
		return occur;
	}
	private static int checkConsecutiveNumbers(String password){
		int occur = 0;
		return occur;
	}
	private static int checkSequentialLetters(String password){
		int occur = 0;
		return occur;
	}
	private static int checkSequentialNumbers(String password){
		int occur = 0;
		return occur;
	}
	private static int checkSequentialSymbols(String password){
		int occur = 0;
		return occur;
	}
	
	
	public static int scorePasswordDummy(String password){
	
		if(password==null)
			return 0;
		
		 switch(password.length()) {
	     case 1 :
	         return 19;
	     case 2:
	         return 39;
	     case 3:
	         return 61;       
	     case 4:
	         return 81;    
	     case 5:
	         return 100;
	     default:
	         return 0;
	 }    

	}
}	
