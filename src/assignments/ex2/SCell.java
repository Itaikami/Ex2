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
     * op represents the current operators "value" ('*','/'=0.5,'+','-'=0,everything inside "()" is raised by 1)
     * issog determines if the current operator is inside "()" or not
     * curr represents the "value" of the operator in the current max index represented by ind
     * @param form the input string representing a formula
     * @return ind representing the index of the main operator
     */
    public static int findIndOfMainOp(String form)
    {
        int ind=-1;
        boolean issog=false;
        double op=0,curr=1.5;//(1+2*2)*3*4
        for (int i = 0; i < form.length(); i++) {
            char c=form.charAt(i);
            if(c=='(')
            {issog=true;}
            if(c==')')
            {issog=false;}
            if(isOp(c))
            {

                switch (c)
                {
                    case '/','*':
                    {
                        op=0.5;
                        break;
                    }
                    case '+','-':
                    {
                        op=0;
                        break;
                    }
                }
                if(issog)
                {op++;}
                if(op<curr||op==curr)
                {
                    ind=i;
                    curr=op;
                }
            }


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
     *  assumes the form is valid and handles some exceptions
     *  calls the function recursively using the last operator that needs to be done
     * @param form the input string
     * @return the calculated value of the formula
     */
    public static double eval(String form) {


        int mainop = findIndOfMainOp(form);
        if (isNumber(form)) {
            return Double.parseDouble(form);
        }
        if (form.charAt(0) == '(' && form.indexOf(')') == form.length() - 1) {
            return eval(form.substring(1, form.length() - 1));
        }
        if (mainop == -1)
        {throw new IllegalArgumentException(Ex2Utils.ERR_FORM);}
            switch (form.charAt(mainop)) {
                case '+': {
                    return (eval(form.substring(0, mainop)) + eval(form.substring(mainop + 1)));
                }
                case '-': {
                    return (eval(form.substring(0, mainop)) - eval(form.substring(mainop + 1)));
                }
                case '*': {
                    return (eval(form.substring(0, mainop)) * eval(form.substring(mainop + 1)));
                }
                case '/': {
                    double denominator = eval(form.substring(mainop + 1));
                    if (denominator == 0) {
                        throw new ArithmeticException("Math_ERR");

                    }
                    return (eval(form.substring(0, mainop)) / eval(form.substring(mainop + 1)));


                }

            }
            return Double.NaN;//invalid value in case the form is wrong
        }
    public static boolean isFormula(String s)//
    {
        boolean ans=true;
        if((s.charAt(0)!='='))
        {ans=false;}
        String form = s.substring(1);

        try
        {
            double val=eval(form);
            if(Double.isNaN(val))
            {
                throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
            }
        }
        catch (Exception e)
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
