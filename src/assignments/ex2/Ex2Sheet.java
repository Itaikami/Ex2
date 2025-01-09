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
        Cell c=get(x,y);
        CellEntry i=new CellEntry(x,y);
        if(canBeComputed(i))
        {if (get(x,y).getType()==Ex2Utils.FORM&&get(x,y).getData().charAt(0)=='=')
            {ans=Double.toString(computeForm(c.getData().substring(1)));}
        else if (get(x,y).getType()==Ex2Utils.FORM) {
            ans=Double.toString(computeForm(c.getData()));
        }
            if(get(x,y).getType()==Ex2Utils.NUMBER)
            {ans=c.getData();}}
        if(c.getType()==Ex2Utils.ERR_CYCLE_FORM&&!c.getData().equals(Ex2Utils.EMPTY_CELL))
        {ans=Ex2Utils.ERR_CYCLE;}
        if(c.getType()==Ex2Utils.TEXT)
            ans=c.getData();
        if(c.getType()==Ex2Utils.ERR_FORM_FORMAT)
            ans=Ex2Utils.ERR_FORM;
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
        CellEntry c = new CellEntry(cords);
        // Add your code here
        try
        {ans=get(c.getX(),c.getY());}
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
        for (int i = 0; i < dd.length; i++) {
            for (int j = 0; j < dd[0].length; j++) {
                CellEntry c=new CellEntry(i,j);
                //if the cell has depth and can be computed set the value to be the calculated cell
                if(dd[i][j]!=-1)
                {
                    eval(i,j);
                }
            }

        }
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
        int t=c.getType();
        CellEntry i=new CellEntry(x,y);
        if(t==Ex2Utils.TEXT||t==Ex2Utils.NUMBER||c.getData().equals(Ex2Utils.EMPTY_CELL))
            return 0;
        if(t==Ex2Utils.ERR_FORM_FORMAT)
        {c.setType(Ex2Utils.ERR_FORM_FORMAT);
        return 0;}

        HashSet<String> ref=reference(c.getData());
        int maxDepth=0;
        if(ref.isEmpty())
        {return 0;}
        for (String s:ref)
        {
            CellEntry current=new CellEntry(s);
            if(isCircular(current,ref))
                return -1;

            if(get(current.getX(),current.getY()).getType()==Ex2Utils.ERR_FORM_FORMAT)
            {get(x,y).setType(Ex2Utils.ERR_FORM_FORMAT);
            return 0;}
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
                CellEntry c=new CellEntry(i,j);

                if(get(c.toString()).getType()==Ex2Utils.FORM)
                {Set<String> ref=reference(get(c.toString()).getData().substring(1));
                if(isCircular(c,ref))
                {ans[i][j]=-1;}}
                if(get(i,j).getType()==Ex2Utils.ERR_FORM_FORMAT)
                    continue;
                ans[i][j]=singleDepth(i,j,ans);
        }}

        return ans;
    }

    /** checks if there is a circular dependency in a cell
     * @param c represents the current cell we are checking
     * @param ref all of c's cell references
     * @return whether there is a circular dependency or not
     */
    public  boolean isCircular(CellEntry c,Set<String> ref)
    {
        if(ref==null)
        {ref=new HashSet<>();}
        String cellInd=c.toString();
        if(ref.contains(cellInd))
        {
            return true;}
        ref.add(cellInd);

        Set<String> curRef=reference(get(c.toString()).getData().substring(1));


        for (String s : curRef) {
            CellEntry nextCell = new CellEntry(s);

            int t=get(nextCell.toString()).getType();
            if(t==Ex2Utils.ERR_CYCLE_FORM&&!get(s).getData().equals(Ex2Utils.EMPTY_CELL))
                return true;
            if(t==Ex2Utils.TEXT||t==Ex2Utils.ERR_FORM_FORMAT||get(nextCell.toString()).getData().equals(Ex2Utils.EMPTY_CELL))
            {
            return false;}

            if (isCircular(nextCell, reference(get(nextCell.toString()).getData()))) {
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
    public static HashSet<String> reference(String s)
    {
        HashSet<String> temp=new HashSet<>();
        if(s==null||s.isEmpty())
            return temp;
        String[] f=s.split("[+/\\-()*]");


        for (int i = 0; i < f.length; i++) {
            f[i]=f[i].toUpperCase();
        }



        for (int i = 0; i < f.length; i++) {
            CellEntry c=new CellEntry(f[i]);
            if(c.isValid())
            {
                temp.add(f[i]);
            }
        }
        return temp;
    } public boolean canBeComputed(CellEntry c) {
        Cell i = get(c.getX(),c.getY());
        if (i.getData().equals(Ex2Utils.EMPTY_CELL)||i.getData().isEmpty()) return false;
        if (i.getType() == Ex2Utils.TEXT) return false;
        if (i.getType() == Ex2Utils.NUMBER) return true;
        if(i.getType()==Ex2Utils.FORM)
        {

            HashSet<String> ref = reference(i.getData().substring(1));
            if(ref.isEmpty())
            {return true;}
            if(isCircular(c,ref))
            {get(c.getX(),c.getY()).setType(Ex2Utils.ERR_CYCLE_FORM);
                return false;}
            for (String s:ref) {
                if(get(s).getData().equals(Ex2Utils.EMPTY_CELL)||get(s).getType()==Ex2Utils.TEXT||get(s).getType()==Ex2Utils.ERR_FORM_FORMAT)
                {get(c.toString()).setType(Ex2Utils.ERR_FORM_FORMAT);
                return false;}
                CellEntry temp = new CellEntry(s);
//                if (!canBeComputed(temp))
//                {   get(c.toString()).setType(Ex2Utils.ERR_FORM_FORMAT);
//                    return false;}

                    HashSet<String>tempRef = reference(get(s).getData());

                    if (isCircular(temp, tempRef)) {
                        get(c.getX(), c.getY()).setType(Ex2Utils.ERR_CYCLE_FORM);
                        return false;
                    }

                    int t = get(temp.toString()).getType();
                    if (t == Ex2Utils.TEXT || t == Ex2Utils.ERR_FORM_FORMAT || get(temp.toString()).getData().equals(Ex2Utils.EMPTY_CELL)) {
                        get(c.toString()).setType(Ex2Utils.ERR_FORM_FORMAT);
                        return false;
                    }
                }



        for (String s : ref) {
            CellEntry current = new CellEntry(s);
            if (!current.isValid())
                return false;
            else if (get(current.toString()).getType() == Ex2Utils.TEXT ||
                    get(current.toString()).getData().equals(Ex2Utils.EMPTY_CELL)) {
                return false;
            }
        }}
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
            if(canBeComputed(c)&&form.charAt(0)=='=')
            {return computeForm(get(c.getX(),c.getY()).getData().substring(1));}
            else if (canBeComputed(c)) {
                return computeForm(get(c.toString()).getData());
            }

        }
        if (mainop == -1) {
            throw new IllegalArgumentException(Ex2Utils.ERR_FORM);}

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
        if(value(x,y).equals(Ex2Utils.EMPTY_CELL))
            return value(x,y);
        String s=get(x,y).getData();
        CellEntry c=new CellEntry(x,y);
        if(canBeComputed(c))
            if(get(x,y).getType()==Ex2Utils.FORM&&!s.equals(Ex2Utils.EMPTY_CELL))
            {ans=Double.toString(computeForm(s.substring(1)));}



            if(get(x,y).getType()==Ex2Utils.NUMBER)
                ans=(get(x,y).getData());
        else if (get(x,y).getType()==Ex2Utils.TEXT) {
            ans=s;
        } else if (get(x,y).getType()==Ex2Utils.ERR_CYCLE_FORM) {
            ans=Ex2Utils.ERR_CYCLE;
        } else if (get(x,y).getType()==Ex2Utils.ERR_FORM_FORMAT) {
            ans=Ex2Utils.ERR_FORM;
        }

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
        if(form.charAt(0)=='('&&form.indexOf(')')==form.length()-1)
        {form=form.substring(1,form.length()-1);}
        CellEntry c=new CellEntry(form);
        //if it is a valid cellEntry then it is a formula
        if(c.isValid())
        {return true;}

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
