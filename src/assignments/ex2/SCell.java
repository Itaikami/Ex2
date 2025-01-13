package assignments.ex2;

/**
 * This Class represents each cell in the spreadsheet and allows us to set a different type for each cell
 * whether it is a text, a number, or a formula. We can perform operations on each cell that isn't text.
 */
public class SCell implements Cell {
    private String line;
    private int type;
    private static Ex2Sheet table;

    public SCell(String s) {
        if (s == null || s.equals(Ex2Utils.EMPTY_CELL)) {
            s = "";
        }
        setData(s);
        setType(deterType(s));
    }

    /**
     * Returns the data of a cell at specific coordinates in the spreadsheet.
     * @param s a string representing the cell in the format of an entry cell.
     * @return the data as a string for the cell at the specified coordinates.
     */
    public static String getData(String s) {
        String a = "";
        Index2D c = new CellEntry(s);
        if (table.isIn(c.getX(), c.getY())) {
            a = table.get(c.getX(), c.getY()).getData();
        }
        return a;
    }

    /**
     * Determines the type of  string (whether it's text, number, formula, or an error).
     * @param s the cell's data string.
     * @return an integer representing the type of the cell data.
     */
    public static int deterType(String s) {
        if (s == null || s.equals(Ex2Utils.EMPTY_CELL)) {
            return Ex2Utils.ERR;
        }
        if (isText(s)) {
            return Ex2Utils.TEXT;
        }
        if (isNumber(s)) {
            return Ex2Utils.NUMBER;
        }
        try {
            boolean formula = isCellFormula(s);
            if (formula) {
                return Ex2Utils.FORM;
            } else {
                return Ex2Utils.ERR_FORM_FORMAT;
            }
        } catch (Exception e) {
            return Ex2Utils.ERR_FORM_FORMAT;
        }
    }

    /**
     * Checks if a string is classified as text.
     * A string is considered text if it is not a number, formula, or does not start with '='.
     * @param s the string to evaluate.
     * @return true if the string is text; otherwise, false.
     */
    public static boolean isText(String s) {
        boolean ans = true;
        if (s.charAt(0) == '=' || isNumber(s)) {
            ans = false;
        }
        return ans;
    }

    /**
     * Checks if a string can be cast to a double without causing an error.
     * @param s the input string.
     * @return true if the string can be parsed as a number; otherwise, false.
     */
    public static boolean isNumber(String s) {
        boolean ans = true;
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            ans = false;
        }
        return ans;
    }

    /**
     * Checks if a string represents a valid cell formula.
     * @param s the string to evaluate.
     * @return true if the string is a valid formula; otherwise, false.
     */
    public static boolean isCellFormula(String s) {
        if (table == null) {
            table = new Ex2Sheet(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
        }
        return table.isFormula(s);
    }

    /**
     * Sets the shared table for SCell operations.
     * @param table the spreadsheet table to associate with the cells.
     */
    public static void setTable(Ex2Sheet table) {
        SCell.table = table;
    }

    @Override
    public String toString() {
        return getData();
    }

    @Override
    public int getOrder() {
        return 0; // No specific order is implemented.
    }

    @Override
    public void setOrder(int t) {
        // Order functionality is not implemented.
    }

    @Override
    public String getData() {
        return line;
    }

    @Override
    public void setData(String s) {
        this.line = s;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        this.type = t;
    }
}
