/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Flavio Prado
 */
public class Datatable {
    private Map<Integer, Map<String,Object> > mData;
    private Vector<String> mColumnNames;

    public Datatable(ResultSet rs)
    {
        this.mColumnNames = new Vector<String>();
        this.mData = new HashMap<Integer, Map<String,Object> >();
        setColumnNames(rs);
        populate(rs);
    }

    private void setColumnNames(ResultSet rs)
    {
        try
        {
            // Get result set meta data
            ResultSetMetaData rsmd = rs.getMetaData() ;
            int numColumns = rsmd.getColumnCount();
            //System.out.println("numColumns: " + numColumns);

            // Get the column names; column indices start from 1
            for (int i=1; i<=numColumns; i++)
            {
                String columnName = rsmd.getColumnName(i);

                mColumnNames.add(columnName);
                //System.out.println(i +"\t".concat(columnName));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void populate(ResultSet rs)
    {
        try
        {
            if ( rs != null )
            {
                while ( rs.next() )
                {
                    Map<String,Object> mpValue = new HashMap<String,Object>();

                    for (int i=1; i<=this.mColumnNames.size(); i++)
                    {
                        String key = this.mColumnNames.get(i-1); //index comeca em 0
                        Object value = rs.getObject(i);
                        value = rs.wasNull() ? "" : value;

                        mpValue.put(key, value);

                        //System.out.println(i +"\t".concat(key).concat("\t").concat(value.toString()));
                    }
                    
                    int key =  rs.getRow();
                    this.mData.put(new Integer(key), new HashMap<String,Object>((mpValue)));

                    //System.out.println("ROW:\t" + key );

                    mpValue.clear();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public int getColumnCount()
    {
        return this.mColumnNames.size();
    }

    public int getRowCount()
    {
        return  this.mData.size();
    }

    public String getColumnName(int col)
    {
        return this.mColumnNames.get(col-1);
    }

    public Object getValueAt(int row, String col)
    {
        Map<String,Object> mpValue = this.mData.get(row);        
        return mpValue.get(col);
    }
}
