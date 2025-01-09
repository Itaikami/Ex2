package assignments.ex2;
// Add your documentation below:

/** This Class represents each cell in the spreadsheet and allows us to set a different type to each cell whether it is
 * a text, a number or a formula, and we can perform our operators with each cell that isn't a text.
 */
public class SCell implements Cell {
    private String line;
    private int type;
    private static Ex2Sheet table;



    public SCell(String s) {
        // Add your code here

        if (s == null || s.equals(Ex2Utils.EMPTY_CELL)) {
            s = "";}
        setData(s);
        setType(deterType(s));



    }

    /** returns the data of a cell in a certain coordinates in the spreadsheet
     * @param s represents a string in a form  of an entry cell
     * @return a string representing the cell in those coordinates
     */
    public static String getData(String s)
{

    String a="";
    Index2D c=new CellEntry(s);
    if (table.isIn(c.getX(),c.getY()))
    {a=table.get(c.getX(), c.getY()).getData();}
    return a;
}
    /**determines the type of string (whether it's text, number, formula or an error)
     *
     * @param s represents the line of a cell
     * @return an int representing the type of s
     */
    public static int deterType(String s)
{
    if (s == null || s.equals(Ex2Utils.EMPTY_CELL)) {
        return Ex2Utils.ERR;}
    if(isText(s))
        return Ex2Utils.TEXT;
    if(isNumber(s))
        return Ex2Utils.NUMBER;
    try {
        boolean formula= isCellFormula(s);

        if(formula)
        {

            return Ex2Utils.FORM;}
        else return Ex2Utils.ERR_FORM_FORMAT;
    }
    catch (Exception e)
    { return Ex2Utils.ERR_FORM_FORMAT;}


}
    /** checks if a string is a "text"
     * if it is not a number or a formula or starts with '=' then it's a text
     * @param s represents the line of a cell
     * @return
     */
    public static boolean isText(String s)
    {

        boolean ans=true;
        if(s.charAt(0)=='='||isNumber(s))
        {ans = false;}
        return ans;
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
       public static boolean isCellFormula(String s)
       {
           if (table == null)
               {table = new Ex2Sheet(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);}
           return  table.isFormula(s);
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
        this.line = s;
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
        this.type = t;
    }
    public static void setTable(Ex2Sheet table) {
        SCell.table = table;
    }


    @Override
    public void setOrder(int t) {
        // Add your code here

    }
}
