package assignments.ex2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
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
    if (!c.getData().equals(Ex2Utils.EMPTY_CELL)) {
        switch (c.getType())
        {
            case Ex2Utils.TEXT, Ex2Utils.NUMBER:
            {ans=c.getData();
                break;}
            case Ex2Utils.FORM:
            {
               ans= Double.toString(computeForm(c.getData())).substring(1);
               break;
            }
            case Ex2Utils.ERR_FORM_FORMAT:
            {
                ans=Ex2Utils.ERR_FORM;
                break;
            }
            case Ex2Utils.ERR_CYCLE_FORM:
            {
                ans=Ex2Utils.ERR_CYCLE;
                break;
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

    /** this function calculates the depth of a singular cell in the table
     * @param x,y represent the coordinates of the cell
     * @return the depth which is 1+max of all the dependencies
     */
    public int singleDepth(int x,int y,int[][] dep)
    {
        //if it is already calculated return it as is
        if(dep[x][y]!=-1)
        {return dep[x][y];}
        Cell c=get(x,y);
        if (c.getType()==Ex2Utils.TEXT)
        {return -1;}
        if (c.getType()==Ex2Utils.NUMBER)
        {    return 0;}
        ArrayList<String> ref=reference(c.getData());
        int maxDepth=0;
        for (String s:ref)
        {
            CellEntry current=new CellEntry(s);
            int sDepth=singleDepth(current.getX(), current.getY(),dep);
            maxDepth=Math.max(sDepth,maxDepth);
        }


        return 1+maxDepth;
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
        }
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                CellEntry c=new CellEntry((char)('A'+i)+Integer.toString(j));
                if (canBeComputed(c))
          ans[i][j]=singleDepth(i,j,ans);
        }}

        return ans;
    }

    /** checks if there is a circular dependency in a cell
     * @param c represents the current cell we are checking
     * @param ref all of c's cell references
     * @return whether there is a circular dependency or not
     */
    public  boolean isCircular(CellEntry c,ArrayList<String> ref)
    {
        String cellInd=c.toString();
        if(ref.contains(cellInd))
        {return true;}
        ref.add(cellInd);
        String val = get(c.getX(), c.getY()).getData();
        //returns all the dependencies of c's content
        ArrayList<String> tlut=new ArrayList<String>(reference(val));
        for (String s : tlut) {
            CellEntry nextCell = new CellEntry(s); // צור CellEntry מה-ref
            if (isCircular(nextCell, ref)) {
                return true;
            }

        }
        ref.remove(cellInd);
        //if we didn't find a cyclical dependency return false
        return false;
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
    public boolean canBeComputed(CellEntry c) {
        Cell i=table[c.getX()][c.getY()];
        if (i.getType() == Ex2Utils.TEXT)
            return false;
        if (i.getType() == Ex2Utils.NUMBER)
            return true;
        if (i.getType() == Ex2Utils.FORM)
        {
           String p=i.getData().substring(1);
           ArrayList<String> ref=reference(p);
            if (isCircular(c,ref))
            {return false;}
            for (String s:ref)
            {
                CellEntry current=new CellEntry(s);
                //if one of the reference call in a cell is empty or a text it cant be computed
                if(get(current.toString()).getType()==Ex2Utils.TEXT||get(current.toString()).getData().equals(Ex2Utils.EMPTY_CELL))
                {return false;}
            }




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
