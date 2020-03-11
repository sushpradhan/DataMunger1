package com.stackroute.datamunger;

/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

   public class DataMunger {
	
		
	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */
	
	public String[] getSplitStrings(String queryString) {
	
		String[] splitstring =  queryString.toLowerCase().split(" ");
		
		return splitstring;
	}

    /*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */

     public String getFileName(String queryString) {
		
		String[] splitstring =  queryString.toLowerCase().split(" ");
		
	/*	for (String filename : splitstring) {
			if(filename.equalsIgnoreCase("ipl.csv");
		}
	*/
		
		return splitstring[3];
		//we have used the index as 3 as the filename will always come in the 3rd iindex only
		//for eg. selct * from ipl.csv -> here ipl.csv is in 4th place i.e. 3rd index
	}

    /*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery(String queryString) {
		if(queryString.toLowerCase().contains("where")) {
			String[] splitstring =  queryString.toLowerCase().split("where");
			return splitstring[0].trim();
		}
		else {
			String[] splitString=queryString.toLowerCase().split("group");
			return splitString[0].trim();
		}
		
	}

    /*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	public String[] getFields(String queryString) {
		
		String[] splitstring =  queryString.toLowerCase().split(" ");
		String[] splitbycomma = splitstring[1].toLowerCase().split(",");
		
		return splitbycomma;
	}

    /*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {
		String query=null;
		if(queryString.toLowerCase().contains(" where "))
		{
		String[] splitstring =  queryString.toLowerCase().split(" where ");
		
		if(splitstring[1].toLowerCase().contains(" order ")) {
			String[] split2 = splitstring[1].toLowerCase().split(" order ");
			query = split2[0];
		}
		
		else if (splitstring[1].toLowerCase().contains(" group ")) {
			String[] split2 = splitstring[1].toLowerCase().split(" group ");
			query = split2[0];
		}
		
		else {
			query = splitstring[1].trim();
		}
		}
		if(!queryString.toLowerCase().contains(" where ")) {
		
			return null;
	}
		return query;
		
	}
	

    /*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all
	 */

     public String[] getConditions(String queryString) {
		
		if(queryString.toLowerCase().contains("where")) {
			String[] splitstring =  queryString.toLowerCase().split("where");
			
			if(splitstring[1].toLowerCase().contains("order")) {
				String[] split2 = splitstring[1].toLowerCase().split("order");
				String[] split3 = split2[0].trim().split(" and | or ");
				return split3;
			}
			
			else if (splitstring[1].toLowerCase().contains("group")) {
				String[] split2 = splitstring[1].toLowerCase().split("group");
				String[] split3 = split2[0].trim().split(" and | or ");
				return split3;
			}
			
			else {
				String[] split3 = splitstring[1].trim().split(" and | or ");
				return split3;
			}
		}
		else return null;
	}

    /*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

     public String[] getLogicalOperators(String queryString) {
		String outand = "";
		
		if(queryString.toLowerCase().contains("or") || queryString.toLowerCase().contains("and")) {
			String[] out = queryString.split(" ");
			
			for (int i=0 ; i<out.length ; i++) {
				if(out[i].equals("and")) {
					outand = outand.concat("and ");
				}
				else if(out[i].equals("or")) {
					outand = outand.concat("or ");
				}
			}
			String[] query = outand.trim().split(" ");
			return query;
		}
		else {
			return null;
		}
	}
	
	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

    public String[] getOrderByFields(String queryString) {
		
		if(queryString.contains(" by ")) {
			String[] query = queryString.split("by ");
			String[] out = query[1].split(" ");
			return out;
		}
		else return null;
	}
/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */

    public String[] getGroupByFields(String queryString) {
		if(queryString.contains(" group by ")) {
			String[] query = queryString.split("by ");
			String[] out = query[1].split(" ");
			return out;
		}
		else return null;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */

    public String[] getAggregateFunctions(String queryString) {
		String check = "";
		if(queryString.contains("min(")|| queryString.contains("max(")|| queryString.contains("avg(")|| queryString.contains(" sum(")|| queryString.contains("count(")) {
			String[] query = queryString.split(" ");
			String[] out = query[1].split(",");
			for(int i=0 ; i<out.length; i++) {
				if(out[i].contains(")")){
					check=check.concat(out[i]+",");
				}
			}
			String[] stringout = check.split(",");
			return stringout;
		}
		else return null;
	}
}