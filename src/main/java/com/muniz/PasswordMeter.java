package com.muniz;

public class PasswordMeter {
	
		
	public int scorePassword(String password) {
				
		int nScore=0, nLength=0, nAlphaUC=0, nAlphaLC=0, nNumber=0, nSymbol=0, nMidChar=0, nRequirements=0, nAlphasOnly=0, nNumbersOnly=0, nUnqChar=0, nRepChar=0,  nConsecAlphaUC=0, nConsecAlphaLC=0, nConsecNumber=0, nConsecSymbol=0, nConsecCharType=0, nSeqAlpha=0, nSeqNumber=0, nSeqSymbol=0, nSeqChar=0, nReqChar=0, nMultConsecCharType=0;
		double nRepInc=0;
		int nMultMidChar=2, nMultConsecAlphaUC=2, nMultConsecAlphaLC=2, nMultConsecNumber=2;
		int nMultSeqAlpha=3, nMultSeqNumber=3, nMultSeqSymbol=3;
		int nMultLength=4, nMultNumber=4;
		int nMultSymbol=6;
		int nTmpAlphaUC =-1, nTmpAlphaLC=-1, nTmpNumber=-1, nTmpSymbol=-1;
		String sAlphaUC="0", sAlphaLC="0", sNumber="0", sSymbol="0", sMidChar="0", sRequirements="0", sAlphasOnly="0", sNumbersOnly="0", sRepChar="0", sConsecAlphaUC="0", sConsecAlphaLC="0", sConsecNumber="0", sSeqAlpha="0", sSeqNumber="0", sSeqSymbol="0";
		String sAlphas = "abcdefghijklmnopqrstuvwxyz";
		String sNumerics = "01234567890";
		String sSymbols = ")!@#$%^&*()";
		int nMinpasswordLen = 8;
		
		if (password.length() > 0) {
			nScore = password.length() * nMultLength;
			nLength = password.length();
			char[] arrpassword = password.toCharArray();
			int arrpasswordLen = arrpassword.length;
			
			/* Loop through password to check for Symbol, Numeric, Lowercase and Uppercase pattern matches */
			for (int a=0; a < arrpasswordLen; a++) {
				
				if (String.valueOf(arrpassword[a]).matches("[A-Z]")) {
					if (nTmpAlphaUC >= 0) { 
						if ( (nTmpAlphaUC + 1) == a) { 
							nConsecAlphaUC++; 
							nConsecCharType++; 
						} 
					}
					nTmpAlphaUC = a;
					nAlphaUC++;
				}
				else if ( String.valueOf(arrpassword[a]).matches("[a-z]")) { 
					if (nTmpAlphaLC >= 0) { 
						if ((nTmpAlphaLC + 1) == a) { 
							nConsecAlphaLC++; 
							nConsecCharType++; 
						} 
					}
					nTmpAlphaLC = a;
					nAlphaLC++;
				}
				else if ( String.valueOf(arrpassword[a]).matches("[0-9]")) { 
					if (a > 0 && a < (arrpasswordLen - 1)) { nMidChar++; }
					if (nTmpNumber >= 0) { if ((nTmpNumber + 1) == a) { nConsecNumber++; nConsecCharType++; } }
					nTmpNumber = a;
					nNumber++;
				}
				else if ( String.valueOf(arrpassword[a]).matches("[^a-zA-Z0-9_]")) { 
					if (a > 0 && a < (arrpasswordLen - 1)) { nMidChar++; }
					if (nTmpSymbol >= 0) { if ((nTmpSymbol + 1) == a) { nConsecSymbol++; nConsecCharType++; } }
					nTmpSymbol = a;
					nSymbol++;
				}
				/* Internal loop through password to check for repeat characters */
				boolean bCharExists = false;
				for (int b=0; b < arrpasswordLen; b++) {
					if (arrpassword[a] == arrpassword[b] && a != b) { /* repeat character exists */
						bCharExists = true;
						/* 
						Calculate icrement deduction based on proximity to identical characters
						Deduction is incremented each time a new match is discovered
						Deduction amount is based on total password length divided by the
						difference of distance between currently selected match
						*/
						nRepInc += Math.abs((double)arrpasswordLen/(b-a));
					}
				}
				if (bCharExists) { 
					nRepChar++; 
					nUnqChar = arrpasswordLen-nRepChar;
					nRepInc = (nUnqChar!=0) ? Math.ceil((double)nRepInc/nUnqChar) : Math.ceil(nRepInc);
				}
			}
			
			/* Check for sequential alpha string patterns (forward and reverse) */
			for (int s=0; s < 23; s++) {
				String sFwd = sAlphas.substring(s,s+3);
				String sRev = new StringBuffer(sFwd).reverse().toString();
				if (password.toLowerCase().indexOf(sFwd) != -1 || password.toLowerCase().indexOf(sRev) != -1) { nSeqAlpha++; nSeqChar++;}
			}
			
			/* Check for sequential numeric string patterns (forward and reverse) */
			for (int s=0; s < 8; s++) {
				String sFwd = sNumerics.substring(s,s+3);
				String sRev = new StringBuffer(sFwd).reverse().toString();
				if (password.toLowerCase().indexOf(sFwd) != -1 || password.toLowerCase().indexOf(sRev) != -1) { nSeqNumber++; nSeqChar++;}
			}
			
			/* Check for sequential symbol string patterns (forward and reverse) */
			for (int s=0; s < 8; s++) {
				String sFwd = sSymbols.substring(s,s+3);
				String sRev = new StringBuffer(sFwd).reverse().toString();
				if (password.toLowerCase().indexOf(sFwd) != -1 || password.toLowerCase().indexOf(sRev) != -1) { nSeqSymbol++; nSeqChar++;}
			}
			
		/* Modify overall score value based on usage vs requirements */

			/* General point assignment */
			if (nAlphaUC > 0 && nAlphaUC < nLength) {	
				nScore = nScore + ((nLength - nAlphaUC) * 2);
				sAlphaUC = "+ " + (nLength - nAlphaUC) * 2; 
			}
			if (nAlphaLC > 0 && nAlphaLC < nLength) {	
				nScore = nScore + ((nLength - nAlphaLC) * 2); 
				sAlphaLC = "+ " + (nLength - nAlphaLC) * 2;
			}
			if (nNumber > 0 && nNumber < nLength) {	
				nScore = nScore + (nNumber * nMultNumber);
				sNumber = "+ " + nNumber * nMultNumber;
			}
			if (nSymbol > 0) {	
				nScore = nScore + (nSymbol * nMultSymbol);
				sSymbol = "+ " + nSymbol * nMultSymbol;
			}
			if (nMidChar > 0) {	
				nScore = nScore + (nMidChar * nMultMidChar);
				sMidChar = "+ " + nMidChar * nMultMidChar;
			}
			
			/* Point deductions for poor practices */
			if ((nAlphaLC > 0 || nAlphaUC > 0) && nSymbol == 0 && nNumber == 0) {  // Only Letters
				nScore = nScore - nLength;
				nAlphasOnly = nLength;
				sAlphasOnly = "- " + nLength;
			}
			if (nAlphaLC == 0 && nAlphaUC == 0 && nSymbol == 0 && nNumber > 0) {  // Only Numbers
				nScore = nScore - nLength; 
				nNumbersOnly = nLength;
				sNumbersOnly = "- " + nLength;
			}
			if (nRepChar > 0) {  // Same character exists more than once
				nScore = (int)(nScore - nRepInc);
				sRepChar = "- " + nRepInc;
			}
			if (nConsecAlphaUC > 0) {  // Consecutive Uppercase Letters exist
				nScore = nScore - (nConsecAlphaUC * nMultConsecAlphaUC); 
				sConsecAlphaUC = "- " + nConsecAlphaUC * nMultConsecAlphaUC;
			}
			if (nConsecAlphaLC > 0) {  // Consecutive Lowercase Letters exist
				nScore = nScore - (nConsecAlphaLC * nMultConsecAlphaLC); 
				sConsecAlphaLC = "- " + nConsecAlphaLC * nMultConsecAlphaLC;
			}
			if (nConsecNumber > 0) {  // Consecutive Numbers exist
				nScore = nScore - (nConsecNumber * nMultConsecNumber);  
				sConsecNumber = "- " + nConsecNumber * nMultConsecNumber;
			}
			if (nSeqAlpha > 0) {  // Sequential alpha strings exist (3 characters or more)
				nScore = nScore - (nSeqAlpha * nMultSeqAlpha); 
				sSeqAlpha = "- " + nSeqAlpha * nMultSeqAlpha;
			}
			if (nSeqNumber > 0) {  // Sequential numeric strings exist (3 characters or more)
				nScore = nScore - (nSeqNumber * nMultSeqNumber); 
				sSeqNumber = "- " + nSeqNumber * nMultSeqNumber;
			}
			if (nSeqSymbol > 0) {  // Sequential symbol strings exist (3 characters or more)
				nScore = nScore - (nSeqSymbol * nMultSeqSymbol); 
			}
			
			int[] arrChars = {nLength,nAlphaUC,nAlphaLC,nNumber,nSymbol};
			String[] arrCharsIds = new String[]{"nLength","nAlphaUC","nAlphaLC","nNumber","nSymbol"};
			int arrCharsLen = arrChars.length;
			int minVal = 0;
			int c=0;
			for (c=0; c < arrCharsLen; c++) {
				if (arrCharsIds[c] == "nLength") { 
					minVal = nMinpasswordLen - 1; 
				} else { 
					minVal = 0; 
				}
			
				if (arrChars[c] == minVal + 1) { 
					nReqChar++; 
				}else if (arrChars[c] > minVal + 1) { 
					nReqChar++; 
				}
			}
			
			int nMinReqChars = 0;
			nRequirements = nReqChar;
			if (password.length() >= nMinpasswordLen) { 
				nMinReqChars = 3; } 
			else { 
				nMinReqChars = 4; }
			
			if (nRequirements > nMinReqChars) {  // One or more required characters exist
				nScore = nScore + (nRequirements * 2); 
				sRequirements = "+ " + (nRequirements * 2);
			}
			
		}
		
		if (nScore > 100) { nScore = 100; } else if (nScore < 0) { nScore = 0; }
		
		return nScore;	
	}
}	
