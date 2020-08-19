package Toxitter.Persistence.persistence.simpledb;

import Toxitter.Model.ReservoirEntity;
import Toxitter.Model.test.TestReservoirEntity;
import Toxitter.Persistence.DataAccessToReservoirEntity;
import Toxitter.Persistence.ReservoirEntityDataPresenter;
import Toxitter.Persistence.annotations.Table;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class SimpleDB
{
    private TreeMap<String, BufferedWriter> streams = new TreeMap<>();
    private String root = "";

    public SimpleDB()
    {

    }

    public void openConnection(String tableName)
    {
        BufferedWriter bw = null;
        try {
            System.out.println(new File(root+tableName));
            bw = new BufferedWriter(new FileWriter(new File(root+tableName)));
            streams.put(tableName, bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(String tableName)
    {
        try {
            BufferedReader br = new BufferedReader((new FileReader(new File(root+tableName))));
            String line = "";
            while ( (line=br.readLine()) != null )
            {
                DataAccessToReservoirEntity ter = new DataAccessToReservoirEntity(new TestReservoirEntity());
                String[] data = line.split(", ");
                for( String entry: data )
                {
                    /*
                    `id` = awnytvyhinieuqrfdrmmiakndxogwjua
                     */
                    String field = entry.substring(0,entry.indexOf(" = ")).replaceAll("`","");
                    String value = entry.substring(entry.indexOf(" = ")+3);
                    System.out.println("Field: "+field);
                    System.out.println("Value: "+value);

                    ter.set(field,value);
                }
                System.out.println("Read Object_ "+ter.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flushAndClose()
    {
        for (Map.Entry<String, BufferedWriter> entry : streams.entrySet())
        {
            BufferedWriter bw = entry.getValue();
            try {
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void put(ReservoirEntity value)
    {
        ReservoirEntityDataPresenter data = new ReservoirEntityDataPresenter(new DataAccessToReservoirEntity(value));
        try {
            streams.get(data.getTable().tableName()).write(data.getFieldEqualsValueSeparatedBy(", ", new SimpleDBTypeTransformer())+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
