package parkettklub.smartcheckroom;

import java.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import parkettklub.smartcheckroom.core.Core;

public class StatisticsActivity extends AppCompatActivity {

    //private XYPlot plot;
    //private PanZoom panZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        initGraph(graph);



        // initialize our XYPlot reference:
        //plot = (XYPlot) findViewById(R.id.plot);

        // set a fixed origin and a "by-value" step mode so that grid lines will
        // move dynamically with the data when the users pans or zooms:
        /*
        plot.setUserDomainOrigin(0);
        plot.setUserRangeOrigin(0);
        */

        // predefine the stepping of both axis
        // increment will be chosen from list to best fit NUM_GRIDLINES grid lines
        /*
        double[] inc_domain = new double[]{10,50,100,500};
        double[] inc_range = new double[]{1,5,10,20,50,100};
        plot.setDomainStepModel(new StepModelFit(plot.getBounds().getxRegion(),inc_domain,NUM_GRIDLINES));
        plot.setRangeStepModel( new StepModelFit(plot.getBounds().getyRegion(),inc_range,NUM_GRIDLINES));
        */


        /*
        // these will be our domain index labels:
        final Time[] hours = {

        };

        // draw a domain tick for each year:
        plot.setDomainStep(StepMode.SUBDIVIDE, hours.length);
        */

        // create a couple arrays of y-values to plot:
        //final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10, 13, 14};
        //Number[] series1Numbers = {1, 4, 2, 8, 4, 16, 8, 32, 16, 64};


        /*
        List<Integer> guestNumbers = Core.listNoneUpdatedTransactions();
        final String[] domainLabels = Core.listNoneUpdatedTransactionTimes().toArray(new String[0]);

        //Number[] domainLabels = new Number[times.size()];
        Number[] series1Numbers = new Number[guestNumbers.size()];

        Integer maxGuests = 0;

        for (int i = 0; i < guestNumbers.size(); i++) {
            series1Numbers[i] = guestNumbers.get(i);
            if(maxGuests < guestNumbers.get(i))
            {
                maxGuests = guestNumbers.get(i);
            }
        }
        */

    }

    private void initGraph(GraphView graph) {

        // generate Dates
        /*
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        */

        Integer maxGuests = 0;

        final List<Date> domainLabels = Core.listNoneUpdatedTransactionTimes();
        final List<Integer> guestNumbers = Core.listNoneUpdatedTransactions();

        DataPoint[] dataPoints = new DataPoint[domainLabels.size()];

        for(int i=0; i < domainLabels.size(); i++)
        {
            dataPoints[i] = new DataPoint(domainLabels.get(i), guestNumbers.get(i));

            if(maxGuests < guestNumbers.get(i))
            {
                maxGuests = guestNumbers.get(i);
            }
        }

        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        graph.addSeries(series);
        series.setDrawDataPoints(true);
        series.setTitle("Number of guests");

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext(),
                DateFormat.getTimeInstance()));

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(domainLabels.get(0).getTime());
        graph.getViewport().setMaxX(domainLabels.get(domainLabels.size()-1).getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(maxGuests+1);
        //raph.getViewport().setYAxisBoundsManual(true);

        // enable scaling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not nessecary
        graph.getGridLabelRenderer().setHumanRounding(true, true);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(120);

    }
}