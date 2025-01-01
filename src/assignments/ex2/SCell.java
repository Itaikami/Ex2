package assignments.ex2;
// Add your documentation below:

/** This Class represents each cell in the spreadsheet abd allows us to set a different type to each cell whether it is
 * a text, a number or a formula, and we can perform our operators with each cell that isn't a text.
 */
public class SCell implements Cell {
    private String line;
    private int type;
    // Add your code here

    public SCell(String s) {
        // Add your code here
        setData(s);
    }

    /** checks if we can cast a string to a double without getting an error
     *
     * @param s the input string
     * @return ans depending on if we caught an error or not
     */
    public static boolean isNumber(String s)
    {
        boolean ans=true;
        double num;
        try {
            num = Double.parseDouble(s);
        }
        catch (NumberFormatException e)
        {
            ans=false;
        }
        return ans;
    }

    /** finds the index of our main operator (the last one we need to calculate)
     *
     * @param form the input string representing a formula
     * @return ind representing the main operator
     */
    public static int findIntOfMainOp(String form)
    {
        int ind=-1;
        double op=0,curr=0;//(1+2)*3*4
        for (int i = 0; i < form.length(); i++) {
            char c=form.charAt(i);
            switch (c)
            {
                case '(':
                    op=op+1;
                    break;
                case ')':
                    op=op-1;
                    break;
                case '*','/':
                    op=0.5;
                    break;

            }
            if((op<curr||op==curr)&&isOp(c))
            {    ind=i;
                curr=op;}

        }

        return ind;
    }

    /** casts a char to a string and checks if it is an Operator
     *
     * @param c represents a char within a formula
     * @return true if c is an operator
     */
    public static boolean isOp(char c)
    {
        boolean ans=false;
        int i=0;
        String s =Character.toString(c);
        while(!ans&&i<Ex2Utils.M_OPS.length)
        {
            if(s.equals(Ex2Utils.M_OPS[i]))
            {    ans=true;}
            i++;


        }
        return ans;
    }

    /** calculates the value of a string representing a formula
     *
     * @param form the input string
     * @return the calculated value of the formula
     */
    public static double eval(String form)
    {
        double sum=-1;
        if(form.charAt(1)=='('&&form.charAt(form.length()-1)==')')
            eval(form.substring(1,form.length()-1));


        return sum;
    }

    public static boolean isFormula(String s)
    {
        boolean ans = true;
        if((s.charAt(0)!='='))
        {ans=false;}
        if(!isNumber(s.substring(1)))
        {ans=false;}

        return ans;
    }

    @Override
    public int getOrder() {
        // Add your code here

        return 0;
        // ///////////////////
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
public void setData(String s) {
        // Add your code here
        line = s;
        /////////////////////
    }
    @Override
    public String getData() {
        return line;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int t) {
        // Add your code here

    }
}
