/*
 *  Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.ibatis.abator.internal.util;

/**
 * @author Jeff Butler
 */
public class JavaBeansUtil {

	/**
	 *  
	 */
	private JavaBeansUtil() {
		super();
	}

	public static String getGetterMethodName(String property) {
		StringBuffer sb = new StringBuffer();

		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}

		sb.insert(0, "get"); //$NON-NLS-1$

		return sb.toString();
	}

	public static String getSetterMethodName(String property) {
		StringBuffer sb = new StringBuffer();

		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}

		sb.insert(0, "set"); //$NON-NLS-1$

		return sb.toString();
	}

	public static String getCamelCaseString(String inputString,
			boolean firstCharacterUppercase) {
		StringBuffer sb = new StringBuffer();

		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			switch (c) {
			case '_':
			case '-':
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
				break;

			default:
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}

		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}

		return sb.toString();
	}
	
	/**
	 * This method ensures that the specified input string is a valid
	 * Java property name as iBATIS sees it.  The rules are as follows:
	 * 
	 * 1. If the first character is lower case, then OK
	 * 2. If the first two characters are upper case, then OK
	 * 3. If the first character is upper case, and the second character is lower case,
	 *    then the first character should be made lower case
	 * 
	 * @param inputString
	 * @return the valid poperty name
	 */
	public static String getValidPropertyName(String inputString) {
	    if (inputString.length() < 2) {
	        return inputString.toLowerCase();
	    }
	    
	    if (Character.isUpperCase(inputString.charAt(0))
	            && Character.isLowerCase(inputString.charAt(1))) {
	        StringBuffer sb = new StringBuffer(inputString);
			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
	        return sb.toString();
	    } else {
	        return inputString;
	    }
	}
}
