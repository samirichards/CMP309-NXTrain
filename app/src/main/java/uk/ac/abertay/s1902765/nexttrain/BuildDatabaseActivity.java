package uk.ac.abertay.s1902765.nexttrain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BuildDatabaseActivity extends AppCompatActivity {

    public static AsyncTask<Void, Void, Void> mTask;
    public static String serverToken = null;
    public static Document stationsDoc = null;

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_database);


        new Thread(new Runnable() {
            @Override
            public void run() {

                switch (getServerToken()){
                    case 0: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getApplicationContext(), "Fetched server token", Toast.LENGTH_SHORT).show();
                            }
                        });
                        try {
                            downloadStationsXML();
                        } catch (IOException e) {
                            e.printStackTrace();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Error downloading stations", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        try {
                            loadStationsIntoSqlCache();
                        } catch (IOException e) {
                            e.printStackTrace();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Error loading stations", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (SAXException e) {
                            e.printStackTrace();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Error loading stations", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Error loading stations", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        TextView helperText = findViewById(R.id.buildingDatabase_currentActionTextView);
                                        helperText.setText("Build Complete");
                                    }
                                });
                                SharedPreferences settings = getSharedPreferences("appSettings", 0);
                                settings.edit().putLong("dbLastUpdated", (new Date(System.currentTimeMillis()).getTime())).apply();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1500);
                            }
                        });
                    }
                    break;
                    case -1: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Incorrect server authentication", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    break;
                    case -2: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    break;
                    case -3: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "An exception occurred", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    break;
                    default: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Something went wrong when fetching server token", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    break;

                }

            }
        }).start();

    }

    protected boolean loadStationsIntoSqlCache() throws IOException, SAXException, ParserConfigurationException {
        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView helperText = findViewById(R.id.buildingDatabase_currentActionTextView);
                helperText.setText("Loading Stations data");
                LinearProgressIndicator progressIndicator = findViewById(R.id.buildingDatabase_progressIndicator);
                progressIndicator.setIndeterminate(false);
            }
        });
        Context context = getApplicationContext();
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        db.clearAllTables();
        //Clear everything initially

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new File(getApplicationContext().getFilesDir() + "/stations.xml"));
        NodeList stations = document.getElementsByTagName("Station");
        handler.post(new Runnable() {
            @Override
            public void run() {
                LinearProgressIndicator progressIndicator = findViewById(R.id.buildingDatabase_progressIndicator);
                progressIndicator.setMax(stations.getLength());
            }
        });
        for (int i = 0; i < stations.getLength(); i++){
            Node station = stations.item(i);
            int finalI = i;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    LinearProgressIndicator progressIndicator = findViewById(R.id.buildingDatabase_progressIndicator);
                    progressIndicator.setProgress(finalI);
                }
            });
            if (station.getNodeType() == Node.ELEMENT_NODE)
            {
                Element stationElement = (Element) station;
                StationItem db_stationItem = new StationItem();
                db_stationItem.Name = stationElement.getElementsByTagName("Name").item(0).getTextContent();
                db_stationItem.CrsCode = stationElement.getElementsByTagName("CrsCode").item(0).getTextContent();
                db_stationItem.SixteenCharacterName = stationElement.getElementsByTagName("SixteenCharacterName").item(0).getTextContent();
                db_stationItem.Latitude = Double.parseDouble(stationElement.getElementsByTagName("Latitude").item(0).getTextContent());
                db_stationItem.Longitude = Double.parseDouble(stationElement.getElementsByTagName("Longitude").item(0).getTextContent());
                db_stationItem.StationOperator = stationElement.getElementsByTagName("StationOperator").item(0).getTextContent();
                db_stationItem.ClosedCircuitTelevision = Boolean.parseBoolean(stationElement.getElementsByTagName("Staffing").item(0).getChildNodes().item(1).getChildNodes().item(0).getTextContent());
                switch (stationElement.getElementsByTagName("Staffing").item(0).getChildNodes().item(0).getTextContent()){
                    case "fullTime":
                        db_stationItem.StaffingLevel = 2;
                        break;
                    case "partTime":
                        db_stationItem.StaffingLevel = 1;
                        break;
                    case "unstaffed":
                        db_stationItem.StaffingLevel = 0;
                        break;

                }
                //TODO Addresses here
                db.stationsDao().insertStation(db_stationItem);
            }
        }
        db.close();
        return true;
    }


    /**
     * Fetches the stations.xml document from the national rail server and stores it on device
     * @return Boolean: True if document was downloaded, false if not
     */
    protected boolean downloadStationsXML() throws IOException {
        if (serverToken!= null){
            URL url = null;
            try {
                url = new URL("https://opendata.nationalrail.co.uk/api/staticfeeds/4.0/stations");
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
            HttpURLConnection http = null;
            try {
                http = (HttpURLConnection) url.openConnection();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            http.setRequestProperty("X-Auth-Token", serverToken);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    TextView helperText = findViewById(R.id.buildingDatabase_currentActionTextView);
                    helperText.setText("Sending request for stations data");
                }
            });
            http.setConnectTimeout(120);


            if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LinearProgressIndicator progressIndicator = findViewById(R.id.buildingDatabase_progressIndicator);
                        progressIndicator.setIndeterminate(true);
                    }
                });

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView helperText = findViewById(R.id.buildingDatabase_currentActionTextView);
                        helperText.setText("Storing stations data");
                    }
                });

                try {
                    FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), "stations.xml"));
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
                    String line;

                    for (line = br.readLine(); line != null; line = br.readLine()) {
                        fos.write(line.getBytes(StandardCharsets.UTF_8));
                    }
                    fos.flush();
                    fos.close();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            LinearProgressIndicator progressIndicator = findViewById(R.id.buildingDatabase_progressIndicator);
                            progressIndicator.setProgress(0);
                            progressIndicator.setMax(100);
                            //Toast.makeText(getApplicationContext(), "Downloaded stations.xml", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Exception occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return false;
                }
            }
            else{
                return false;
            }

        }
        else{
            return false;
        }
    }

    /**
     * Function to get a session token from the national rail server
     * Returns 0 when token generation is successful
     * -1 when authentication is incorrect
     * -2 when there is a server error
     * -3 when there is a general exception
     * @return
     */
    protected int getServerToken(){

        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView helperText = findViewById(R.id.buildingDatabase_currentActionTextView);
                helperText.setText("Fetching token from server");
            }
        });

        URL url = null;
        try {
            url = new URL("https://opendata.nationalrail.co.uk/authenticate");
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) url.openConnection();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        http.setDoOutput(true);
        http.setInstanceFollowRedirects(false);
        http.setRequestProperty("Accept","*/*");
        try {
            http.setRequestMethod("POST");
        } catch (ProtocolException protocolException) {
            protocolException.printStackTrace();
        }
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String data = "username=samirichards100@gmail.com&password=cizP4wFajM8ma!m";
        try {
            http.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int status = 0;
        try {
            status = http.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK)  {
                BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                String responseMessage = sb.toString();

                JSONObject jsonResponse = new JSONObject(responseMessage);
                if (jsonResponse.has("error")){
                    http.disconnect();
                    return -1;
                }
                else{
                    serverToken = jsonResponse.get("token").toString();
                    http.disconnect();
                    return 0;
                }
            }
            else  {
                http.disconnect();
                return -2;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            http.disconnect();
            return -3;
        }
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "Generating database, please wait", Toast.LENGTH_SHORT).show();
    }
}