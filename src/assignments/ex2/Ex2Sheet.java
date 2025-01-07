package assignments.ex2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // Add your code here

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new Cell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell("");
            }
        }
        eval();
    }
    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        // Add your code here
if(isIn(x, y)) {
    Cell c = get(x, y);
    if (c != null) {
        switch (c.getType())
        {
            case Ex2Utils.TEXT, Ex2Utils.NUMBER:
            {ans=c.getData();
                break;}
            case Ex2Utils.FORM:
            {
               ans= Double.toString(computeForm(c.getData()));
            }
        }
    }
}
        /////////////////////
        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        Cell ans = null;
        // Add your code here
        try
        {ans=table[CellEntry.deterX(cords.substring(0,1))][Integer.parseInt(cords.substring(1))];}
        catch (ArrayIndexOutOfBoundsException|NumberFormatException e)
        {
            return null;
        }
        /////////////////////
        return ans;
    }

    @Override
    public int width() {
        return table.length;
    }
    @Override
    public int height() {
        return table[0].length;
    }
    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        // Add your code here

        /////////////////////
    }
    @Override
    public void eval() {
        int[][] dd = depth();
        // Add your code here

        // ///////////////////
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans=true;
        // Add your code here
        try {
            ans=(xx>=0&&xx<width()&&yy>=0&&yy< height());
        }
        catch (ArrayIndexOutOfBoundsException e)
        {  ans=false;}
        /////////////////////
        return ans;
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        // Add your code here
        int d = 0, cnt = 0, max = width() * height();
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                ans[i][j] = -1;

            }

            // ///////////////////

        }
        return ans;
    }
    public  boolean isCirular(CellEntry c,ArrayList<String> all)
    {
        String val=get(c.getX(),c.getY()).getData();
       ArrayList<String> s=reference(val);
       if(s.contains(val))
       {return true;}
        s.add(val);



    }

    /**recives a string and returns an array containing its cell reference calls
     * @param s the string representing a formula
     * @return the array containing all the reference calls
     */
    public static ArrayList<String> reference(String s)
    {

        String[] f=s.split("[+/\\-()*]");
        ArrayList<String> temp=new ArrayList<>();
        for (int i = 0; i < f.length; i++) {
            CellEntry c=new CellEntry(f[i]);
            if(c.isValid())
            {
                temp.add(f[i]);
            }
        }
        return temp;
    }
    public static boolean canBeComputed(SCell c) {

        if (c.getType() == Ex2Utils.TEXT)
            return true;
        if (c.getType() == Ex2Utils.NUMBER)
            return true;
        if (c.getType() == Ex2Utils.FORM)
        {
           String p=c.getData().substring(1);
            //let's check if it's only a Cell entry
            CellEntry i=new CellEntry(p);




    }
return true;
    }


    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }
    /** calculates the value of a string representing a formula
     *  assumes the form is valid and handles some exceptions
     *  calls the function recursively using the last operator that needs to be done
     * @param form the input string
     * @return the calculated value of the formula
     */

    public double computeForm(String form) {
        int mainop = findIndOfMainOp(form);
        try {
           double num= Double.parseDouble(form);
           return num;
        } catch (NumberFormatException _) {

        }
        if (form.charAt(0) == '(' && form.indexOf(')') == form.length() - 1) {
            return computeForm(form.substring(1, form.length() - 1));
        }
        CellEntry c = new CellEntry(form);
        if (c.isValid()&&isIn(c.getX(),c.getY())) {
            return computeForm(eval(c.getX(),c.getY()));
        }
        if (mainop == -1) {
            throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
        }
        switch (form.charAt(mainop)) {
            case '+': {
                return (computeForm(form.substring(0, mainop)) + computeForm(form.substring(mainop + 1)));
            }
            case '-': {
                return (computeForm(form.substring(0, mainop)) - computeForm(form.substring(mainop + 1)));
            }
            case '*': {
                return (computeForm(form.substring(0, mainop)) * computeForm(form.substring(mainop + 1)));
            }
            case '/': {
                double denominator = computeForm(form.substring(mainop + 1));
                if (denominator == 0) {
                    throw new ArithmeticException("Math_ERR");

                }
                return (computeForm(form.substring(0, mainop)) / computeForm(form.substring(mainop + 1)));


            }

        }
        return Double.NaN;//invalid value in case the form is wrong
    }
    @Override
    public String eval(int x, int y) {
        String ans = null;
        if(get(x,y)!=null) {ans = get(x,y).toString();}



        // Add your code here

        /////////////////////
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
    /** receives a String and checks if it's a valid formula
     * @param s
     * @return true if it's a valid formula and false if it isn't
     */
    public  boolean isFormula(String s)//
    {
        if((s.charAt(0)!='='))
        {return false;}

        String form = s.substring(1);
        CellEntry c=new CellEntry(form);
        //if it is a valid cellEntry check if its data after calculation
        if(c.isValid()&&!get(c.getX(),c.getY()).getData().equals(Ex2Utils.EMPTY_CELL))
        {return isFormula("="+eval(c.getX(),c.getY()));}

        try {
            int opIndex = findIndOfMainOp(form);
            if (opIndex == -1) {
                //if we don't have an op lets see if it's a number
                if(Double.parseDouble(form)==computeForm(form)) {
                    return true;
                }
            }
            //if we find an op we call the function recursively with its left and right sides
            String lhs = "=" + form.substring(0, opIndex);
            String rhs = "=" + form.substring(opIndex+1);
            return isFormula(lhs) && isFormula(rhs);}
        catch (Exception e)
        {return false;}
}}
