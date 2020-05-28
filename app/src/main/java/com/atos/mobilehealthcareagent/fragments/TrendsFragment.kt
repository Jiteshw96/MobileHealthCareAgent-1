package com.atos.mobilehealthcareagent.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.fragment_trends.*
import java.util.*

class TrendsFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trends, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set Progress
        // Set Progress
        circularProgressBar.progress = 65f

        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        chart.setBackgroundColor(Color.parseColor("#99BABABA"))
        chart.setGridBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        val values =
            ArrayList<Entry>()
        values.add(Entry(0f, 10000f))
        values.add(Entry(1f, 20000f))
        values.add(Entry(2f, 12400f))
        values.add(Entry(3f, 11443f))
        values.add(Entry(4f, 300f))
        values.add(Entry(5f, 25000f))
        values.add(Entry(6f, 0f))
        displayData(chart, values)

    }

    private fun displayData(
        mChart: LineChart,
        values: ArrayList<Entry>
    ) {
        val set1: LineDataSet
        if (mChart.data != null && mChart.data.dataSetCount > 0) {
            set1 = mChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            mChart.data.notifyDataChanged()
            mChart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, "Sample Data")
            set1.setDrawIcons(false)
            //  set1.enableDashedLine(10f, 5f, 0f);
            //  set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.color = Color.WHITE
            set1.setCircleColor(Color.WHITE)
            set1.lineWidth = 2f
            set1.circleRadius = 4f
            set1.setDrawCircleHole(false)
            set1.setDrawValues(false)
            set1.setDrawFilled(true)
            set1.formLineWidth = 1f
            //  set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            //  set1.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {
                val drawable =
                    ContextCompat.getDrawable(activity!!, R.drawable.fade_blue)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.DKGRAY
            }
            val xValues =
                arrayOf("S", "M", "T", "W", "T", "F", "S")
            val xAxis = mChart.xAxis
            xAxis.valueFormatter =
                IAxisValueFormatter { value, axis -> xValues[value.toInt()] }
            xAxis.granularity = 1f
            xAxis.gridColor = Color.WHITE
            xAxis.textColor = Color.WHITE
            xAxis.textSize = 12f
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            val yValues =
                arrayOf("0", "10", "20", "30", "40", "50")
            val yAxis = mChart.axisLeft
            //yAxis.setDrawZeroLine(true);
            // yAxis.setDrawTopYLabelEntry(true);
            yAxis.valueFormatter =
                IAxisValueFormatter { value, axis -> yValues[(value / 10000).toInt()] }
            yAxis.granularity = 1f
            yAxis.gridColor = Color.WHITE
            mChart.axisRight.gridColor = Color.WHITE
            yAxis.setDrawLabels(true)
            yAxis.labelCount = 2
            yAxis.setDrawGridLines(true)
            mChart.axisRight.setDrawLabels(false)
            yAxis.textColor = Color.WHITE
            yAxis.setDrawAxisLine(true)
            yAxis.textSize = 14f
            yAxis.axisLineColor = Color.WHITE
            yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            val data = LineData(dataSets)
            mChart.data = data
        }
    }
}
