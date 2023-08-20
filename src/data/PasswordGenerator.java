/**
*   SecretNotes stores data encrypted with password
*   Copyright (C) 2003 Simone Pernice <pernice@libero.it>
*
*   This file is part of SecretNotes.
*
*   SecretNotes is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   SecretNotes is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*/


/**
 * @author Darron Schall
 * @version 1.0
 * 
 * Aug. 22, 2003
 * 
 * PasswordGenerator for creating random passwords.  
 * 
 * Four password flags are available to dictate generation,
 * or a template can be specificed to generate a string with 
 * a specified character type at each position.
 * 
 * Revision History:
 * Rev Date			Who		Description
 * 1.0 8/22/03		darron	Initial Draft
 * --------------------------------------
 * License For Use
 * --------------------------------------
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The name of the author may not be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 *
 * Adapted to J2ME by Simone Pernice
 * 30th November 2007
 * Using SecureRandom from BouncyCastle to improve security
 */

package data;

//Simone Pernice I used Vector because ArrayList is not available in J2ME
import java.util.Vector;
//Simone Pernice I used SecureRandom from BouncyCastle because much better than standard Random Generator (Math.Random() is not available in J2ME)


// Re-use some existing exceptions so we don't have to create
// our own.
//Simone Pernice I removed all the exceptions to simplify the class

public class PasswordGenerator {
        private static SecureRandom secureRandom = new SecureRandom ();
    
	private int length;
	
	private boolean lowercaseIncluded;
	private boolean numbersIncluded;
	private boolean othersIncluded;
	private boolean uppercaseIncluded;

	private StringBuffer password; //Simone Pernice I used a StringBuffer to improve the efficiency
	private String template;

	public PasswordGenerator() {
		// by default, invlude lowercase, uppercase, and numbers
		// in the password, and make it 8 characters long.
		password = new StringBuffer ();
		template = "";
		length = 8;
		lowercaseIncluded = true;
		uppercaseIncluded = true;
		numbersIncluded = true;
		othersIncluded = false;
		
		// start the ball rolling by generating a password so that
		// we keep our data integrity 
		// i.e. so length matches password.length());
		generatePassword();
	}

	/**
	 * @return true if at least one of the password generation flags
	 * is true, otherwise returns false
	 */
	private boolean flagsOK() {
		return lowercaseIncluded
			|| uppercaseIncluded
			|| numbersIncluded
			|| othersIncluded;
	}

	/**
	 * @return a random lowercase character from 'a' to 'z'
	 */
	private static char randomLowercase() {
		return (char) (97 + Math.abs(secureRandom.nextInt() % 26));
	}

	/**
	 * @return a random uppercase character from 'A' to 'Z'
	 */
	private static char randomUppercase() {
		return (char) (65 + Math.abs(secureRandom.nextInt() % 26));
	}

	/**
	 * @return a random character in this list: !"#$%&'()*+,-./
	 */
	private static char randomOther() {
		return (char) (33 + Math.abs(secureRandom.nextInt() % 15));
	}

	
	/**
	 * @return a random character from '0' to '9'
	 */
	private static char randomNumber() {
		return (char) (48 + Math.abs(secureRandom.nextInt() % 10));
	}

	public void generatePassword() /* throws InvalidPreferencesFormatException, ParseException */ {
		// clear password if necessary
		if (password.length() != 0) {
			password = new StringBuffer();
		}

		// check to make sure at least one "type" is included
		// for password generation if template is not defined
		
		// commented out because our setters/construcor should
		// ensure data integrity
		//if (!flagsOK() && template.length() == 0) {
		//	throw new java.util.prefs.InvalidPreferencesFormatException("At least one flag must be on to generate a password");
		//}
		

		// we know length >= 1 here because setLength
		// doesn't allow invalid lengths and the constructor
		// initializes length to 8

		// a template being defined overrides all flags
		if (template.length() > 0) {
			length = template.length();
			for (int i = 0; i < length; i++) {
				switch (template.charAt(i)) {
					case 'l' :
                                        case 'L' :
						password.append(randomLowercase());
						break;

					case 'U' :
                                        case 'u' :
						password.append(randomUppercase());
						break;

					case 'n' :
					case 'N' :
						password.append(randomNumber());
						break;

					case 'o' :
					case 'O' :
						password.append(randomOther());
						break;

					// commented out because our setters/constructor
					// should ensure data integrity
					//default :
					//	throw new ParseException("Password template contains an invalid character", i);
				}
			}
		} else {
			// In Java we can't create an array of function references
			// so I've created 4 "wrapper" classes that inherit from the
			// randomCharacter interface to provide the same
			// type of functionality.
			
			
			// create an Vector to store the functions that we're allowed
			// to call to generate the password, based on what the flags
			// are set to.
			Vector flags = new Vector();
			if (lowercaseIncluded) {
				flags.addElement(new randomLowercase());
			}
			if (uppercaseIncluded) {
				flags.addElement(new randomUppercase());	
			}
			if (othersIncluded) {
				flags.addElement(new randomOther());	
			}
			if (numbersIncluded) {
				flags.addElement(new randomNumber());	
			}
			
			int flagLength = flags.size();
			
			for (int i = 0; i < length; i++) {
				// get a random wrapper class from the flags Vector
				// and cast it to our interface so we can call the execute method
				// which just calls the function and returns its value.
				password.append(((randomCharacter)flags.elementAt(Math.abs(secureRandom.nextInt() % flagLength))).execute());
			}
		}
	}

	/**
	 * @return the length of the generated password
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the generated password
	 */
	public String getPassword() {
		return password.toString();
	}

	/**
	 * @return password template
	 */
	public String getTemplate() {
		return template;
	}
	/**
	 * @return lowercaseIncluded
	 */
	public boolean isLowercaseIncluded() {
		return lowercaseIncluded;
	}

	/**
	 * @return numbersIncluded
	 */
	public boolean isNumbersIncluded() {
		return numbersIncluded;
	}

	/**
	 * @return othersIncluded
	 */
	public boolean isOthersIncluded() {
		return othersIncluded;
	}

	/**
	 * @return uppercaseIncluded
	 */
	public boolean isUppercaseIncluded() {
		return uppercaseIncluded;
	}

	/**
	 * @param length, enforced to be a positive integer >= 3.
	 */
	public void setLength(int length) {
		this.length = (length < 3) ? 3 : length;
	}

	/**
	 * @param b
	 */
	public void setLowercaseIncluded(boolean b) {
		lowercaseIncluded = b;

		// did we turn off the last flag?  if so
		// turn it back on and report error
		if (b == false && !flagsOK()) {
			lowercaseIncluded = true;
		}
	}

	/**
	 * @param b
	 */
	public void setNumbersIncluded(boolean b) {
		numbersIncluded = b;

		// did we turn off the last flag?  if so
		// turn it back on and report error
		if (b == false && !flagsOK()) {
			numbersIncluded = true;
		}
	}

	/**
	 * @param b
	 */
	public void setOthersIncluded(boolean b) {
		othersIncluded = b;

		// did we turn off the last flag?  if so
		// turn it back on and report error
		if (b == false && !flagsOK()) {
			othersIncluded = true;
		}
	}

	/**
	 * @param string
	 */
	public void setTemplate(String template) {
		// make sure the template contains only legal characters
		for (int i = 0; i < template.length(); i++) {
			switch (template.charAt(i)) {
				case 'l' :
				case 'L' :
				case 'u' :
				case 'U' :                                    
				case 'n' :
				case 'N' :
				case 'o' :
				case 'O' :
					break;					
				default :
					return;
			}
		}
		this.template = template;
	}
	
	/**
	 * Clears the password template,making generation rely on the flags.
	 *
	 */
	public void clearTemplate() {
		template = "";	
	}

	/**
	 * @param b
	 */
	public void setUppercaseIncluded(boolean b) {
		uppercaseIncluded = b;

		// did we turn off the last flag?  if so
		// turn it back on and report error
		if (b == false && !flagsOK()) {
			uppercaseIncluded = true;	
		}
	}

	/*--------------------------------------------------------
	 Wrapper classes and interface to mimic the array of 
	 function references functionality required.
 	----------------------------------------------------------*/
 	private static class randomLowercase implements randomCharacter {
		public char execute() {
			return PasswordGenerator.randomLowercase();
		}
	}

	private static class randomUppercase implements randomCharacter {
		public char execute() {
			return PasswordGenerator.randomUppercase();
		}
	}

	private static class randomOther implements randomCharacter {
		public char execute() {
			return PasswordGenerator.randomOther();
		}
	}

	private static class randomNumber implements randomCharacter {
		public char execute() {
			return PasswordGenerator.randomNumber();
		}
	}

	private static interface randomCharacter {
		char execute();
	}

}
