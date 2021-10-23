package automate.capstone.feeder.DataRecycler;

/**
 * Created by Donnald on 2/7/2018.
 */

public class DataSchedule {
    public String sched_name;
    public String start_date;
    public String end_date;
    public String feed_amount;
    public String date_added;
    public String id;

    public String getSched_name() {
        return sched_name;
    }

    public void setSched_name(String sched_name) {
        this.sched_name = sched_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getFeed_amount() {
        return feed_amount;
    }

    public void setFeed_amount(String feed_amount) {
        this.feed_amount = feed_amount;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
