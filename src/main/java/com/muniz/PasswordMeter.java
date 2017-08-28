package com.muniz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.text.html.HTMLDocument.HTMLReader.CharacterAction;

public class PasswordMeter {
	
	private enum Score {
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
	
	public int scorePassword(String password) {
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
	
	private List<String> buildCheckVector(String password){
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
	
	
	private String checkScore(String password,Score scoreType){
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
				rate = rate.replaceAll("len-n", Integer.toString(calc));
			}else
				rate = rate.replaceAll("n", Integer.toString(occur));
		}	
		
		return rate;
	}
	
	
	//Additions
	private int checkNumberOfCharacters(String password){
		int occur = password.length();
		return occur;
	}
	
	private boolean hasUpperLetters = false;
	private int checkUppercaseLetters(String password){
		String upper = password.chars()
                .filter(Character::isUpperCase)
                .mapToObj(c -> Character.toString((char)c))
                .collect(Collectors.joining());
		int occur = upper.length();
		
		if(occur>0)
			hasUpperLetters = true;
		
		return occur;
	}
	
	private boolean hasLowerLetters = false;
	private int checkLowercaseLetters(String password){
		String lower = password.chars()
                .filter(Character::isLowerCase)
                .mapToObj(c -> Character.toString((char)c))
                .collect(Collectors.joining());
		
		int occur = lower.length();
		if(occur>0)
			hasLowerLetters = true;
		
		return occur;
	}
	
	private boolean hasNumber = false;
	private int checkNumbers(String password){
		String digits = password.replaceAll("[^0-9.]", "");	
		int occur = digits.length();
		
		if(occur>0)
			hasNumber = true;
		
		return occur;
	}
	
	private boolean hasSymbols = false;
	private int checkSymbols(String password){
		String digits = password.replaceAll("[^_+-.,!@#$%^&*();\\/|<>\"']", "");	
		int occur = digits.length();
		
		if(occur>0)
			hasSymbols = true;
		
		return occur;
	}
	private int checkMiddleNumbersOrSymbols(String password){
		int occur = 0;
				
		for(int i=1;i<password.length()-1;i++){
			char c = password.charAt(i); 
			if( Character.isDigit(c) || !Character.isLetterOrDigit(c))
				occur++;
		}
				
		return occur;
	}
	private int checkRequirements(String password){
		int occur = 0;
		
		if(password.length()>=8)
			occur++;
		
		if(hasSymbols)
			occur++;
		
		if(hasLowerLetters)
			occur++;
		
		if(hasNumber)
			occur++;
		
		if(hasUpperLetters)
			occur++;
				
		return occur>=4?occur:0;
	}
	
	//Deductions
	private int checkLettersOnly(String password){
		int occur = 0;
		
		if(!hasNumber && !hasSymbols)
			occur = password.length();
		
		return occur;
	}
	
	private int checkNumbersOnly(String password){
		int occur = 0;
		
		if(!hasUpperLetters && !hasSymbols && !hasLowerLetters)
			occur = password.length();
		
		return occur;
	}
	
	// source not avaliable
	private int checkRepeatCharacters(String password){
		int occur = 0;
		return occur;
	}
	
	
	private int checkConsecutiveUppercaseLetters(String password){
		int occur = 0;
		
		for(int i=1;i<password.length();i++){
			if(Character.isUpperCase(password.charAt(i)) && Character.isUpperCase(password.charAt(i-1)))
				occur++;
		}
		
		return occur;
	}
	private int checkConsecutiveLowercaseLetters(String password){
		int occur = 0;
		
		for(int i=1;i<password.length();i++){
			if(Character.isLowerCase(password.charAt(i)) && Character.isLowerCase(password.charAt(i-1)))
				occur++;
		}
				
		return occur;
	}
	private int checkConsecutiveNumbers(String password){
		int occur = 0;
		
		for(int i=1;i<password.length();i++){
			if(Character.isDigit(password.charAt(i)) && Character.isDigit(password.charAt(i-1)))
				occur++;
		}
		
		return occur;
	}
	
	private int checkSequentialLetters(String password){
		int occur = 0;
		
		password = password.toLowerCase();
		
		for(int i=2;i<password.length();i++){
			char c = password.charAt(i);
			char c2 = password.charAt(i-1);
			char c3 = password.charAt(i-2);
			if(Character.isLetter(c) && Character.isLetter(c2) && Character.isLetter(c3)){
				if (c-1 == c2 && c-2 == c3)
					occur++;
					
			}	
		}
		
		return occur;
	}
	private int checkSequentialNumbers(String password){
		int occur = 0;
		
		for(int i=2;i<password.length();i++){
			char c = password.charAt(i);
			char c2 = password.charAt(i-1);
			char c3 = password.charAt(i-2);
			if(Character.isDigit(c) && Character.isDigit(c2) && Character.isDigit(c3)){
				if (c-1 == c2 && c-2 == c3)
					occur++;
			}	
		}
				
		return occur;
	}
	private int checkSequentialSymbols(String password){
		int occur = 0;
		
		for(int i=2;i<password.length();i++){
			char c = password.charAt(i);
			char c2 = password.charAt(i-1);
			char c3 = password.charAt(i-2);
			if(!Character.isLetterOrDigit(c) && !Character.isLetterOrDigit(c2) && !Character.isLetterOrDigit(c3)){
				if (c-1 == c2 && c-2 == c3)
					occur++;
	
			}	
		}
		
		return occur;
	}
	
	
	public int scorePasswordDummy(String password){
	
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
