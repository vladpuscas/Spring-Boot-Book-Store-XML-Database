package application.Report;

/**
 * Created by Vlad on 14-Apr-17.
 */
public class ReportFactory {

    public Report getReport(String type) {
        if (type == null)
            return null;
        if(type.equalsIgnoreCase("PDF")) {
            return new PDFReport();
        }
        else if(type.equalsIgnoreCase("CSV")) {
            return new CSVReport();
        }
        return null;
    }
}
