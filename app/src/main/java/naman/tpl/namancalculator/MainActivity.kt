package naman.tpl.namancalculator

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    var tvSelectedDate:TextView?=null
    var tvSelectedAge:TextView?=null
    var tvSelectedDays:TextView?=null
    var tvSelectedHrs:TextView?=null
    var tvSelectedMins:TextView?=null
    var tvSelectedSecs:TextView?=null
    data class diff(val days: Int, val months: Int, val yrs: Int)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnDatePicker: Button=findViewById(R.id.dtPicker)
        tvSelectedDate=findViewById(R.id.dtView)
        tvSelectedAge=findViewById(R.id.tvage)
        tvSelectedDays=findViewById(R.id.ageday)
        tvSelectedHrs=findViewById(R.id.agehour)
        tvSelectedMins=findViewById(R.id.agemins)
        tvSelectedSecs=findViewById(R.id.agesecs)
        btnDatePicker.setOnClickListener {
            Toast.makeText(this,"Select the DOB",Toast.LENGTH_SHORT).show()
            datePick()
        }

    }
    fun datePick(){
        val myCalendar=Calendar.getInstance()
        val year=myCalendar.get(Calendar.YEAR)
        val month=myCalendar.get(Calendar.MONTH)
        val day=myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd=DatePickerDialog(this,
            {view, Selectedyear,Selectedmonth,SelectedDayOfMonth->
                //Toast.makeText(this,"Datepicker works",Toast.LENGTH_LONG).show()
                var selday: String?;
                var selmonth: String?;
                if(SelectedDayOfMonth<10){
                    selday="0${SelectedDayOfMonth}"
                }else{
                    selday="$SelectedDayOfMonth"
                }

                if(Selectedmonth<9){
                    selmonth="0${Selectedmonth+1}"
                }else{
                    selmonth="${Selectedmonth+1}"
                }
                var selectedDate="${selday}/${selmonth}/${Selectedyear}"
                tvSelectedDate?.text=selectedDate
                calulate(selectedDate)
            },
            year,
            month,
            day
        )
        dpd.show()
    }
    fun calulate(dob:String){
        val sdf=SimpleDateFormat("dd/MM/yyyy")
        val dobtime=sdf.parse(dob)
        val curDate=Date()
        val diffmili=curDate.time - dobtime.time
        //println("----------------------------------------------------------------------=${dob}")
        val agesecs=(diffmili)/1000
        val ageminutes=agesecs/60
        val agehrs=ageminutes/60
        val agedays=agehrs/24
        val age=getdiff(dob)
        tvSelectedSecs?.text="In secs: ${agesecs.toString()}"
        tvSelectedAge?.text="Your age is ${age.yrs} yrs ${age.months} months and ${age.days} days"
        tvSelectedMins?.text="In mins: ${ageminutes.toString()}"
        tvSelectedDays?.text="In Days: ${agedays.toString()}"
        tvSelectedHrs?.text="In hours: ${agehrs.toString()}"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getdiff(dob:String): diff {
        // parse the date with a suitable formatter
        val from = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        // get today's date
        val today = LocalDate.now()
        // calculate the period between those two
        var period = Period.between(from, today)
        // and print it in a human-readable way
        println("--------------------------------------------${dob}")
        val di=diff(period.days,period.months,period.years)
        return di
    }
}

