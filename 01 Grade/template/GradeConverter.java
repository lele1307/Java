import java.io.*;
// Class to convert unit marks to grades
class GradeConverter
{
    // Converts a numerical mark (0 to 100) into a textual grade
    // Returns "Invalid" if the number is invalid
    String convertMarkToGrade(final int mark)
    {
        if (mark < 0 || mark > 100) {
            //System.out.println("YOU CANNOT INPUT OUT OF 0-100!!");
            return "Invalid";
		} else if (mark >= 70) {
            //System.out.println("Distinction");
            return "Distinction";
		} else if (mark >= 60) {
            //System.out.println("Merit");
            return "Merit";
		} else if (mark >= 50) {
            //System.out.println("Pass");
            return "Pass";
		} else {
            //System.out.println("Fail");
            return "Fail";
		}
    }

    // Reads a mark from a String and returns the mark as an int (0 to 100)
    // Returns -1 if the string is invalid
    int convertStringToMark(final String text)
    {
        int length;
        int result=0,flag;
        int j=0,k=0;
        char single;
        int spstr = -1;
        length = text.length();
        if(length>=6 || length <= 0)
        {
            return -1;
        }
        else
        {
            if(length>1)
            {
                single = text.charAt(0);
                if(single=='0')
                {
                    return -1;
                }
            }
            for(int i = 0; i < length; i++) 
            {
                single = text.charAt(i);
                if(convertCharToInt(single) == -1)
                {
                    spstr = i;
                }
            }
            if(spstr >= 0)
            {

            }
            else
            {
                flag = length;
                for(int i = 0; i < length; i++) 
                {
                    single = text.charAt(i);
                    if(flag>2)
                    {
                        j = convertCharToInt(single)*100;
                    }
                    else if(flag>1)
                    {
                        k = convertCharToInt(single)*10+j;
                    }
                    else if(flag>0)
                    {
                        result = convertCharToInt(single)+k;
                    }
                }
                flag--;
            }
        }
        
        if(result>100)
        {
            return -1;
        }
        else
        {
            return result;
        }
    } 
    // Convert a single character to an int (0 to 9)
    // Returns -1 if char is not numerical
    int convertCharToInt(final char c)
    {
        int nc;
        if (Character.isDigit(c)==true) {
            nc = c - '0';
            return nc;
        }
        else
        {
            return -1;
        }
    }

    public static void main(final String[] args) throws IOException
    {
        final GradeConverter converter = new GradeConverter();
        while(true) {
            System.out.print("Please enter your mark: ");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            final String input = reader.readLine();
            final int mark = converter.convertStringToMark(input);
            final String grade = converter.convertMarkToGrade(mark);
            System.out.println("A mark of " + input + " is " + grade);
        }
    }
}
