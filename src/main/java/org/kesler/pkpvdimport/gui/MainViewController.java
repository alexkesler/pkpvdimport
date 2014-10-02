package org.kesler.pkpvdimport.gui;

import org.kesler.pkpvdimport.domain.*;
import org.kesler.pkpvdimport.export.XLSXExportEnum;
import org.kesler.pkpvdimport.export.XLSXExporterFactory;
import org.kesler.pkpvdimport.pvdimport.ReaderListener;
import org.kesler.pkpvdimport.pvdimport.support.PackagesReader;
import org.kesler.pkpvdimport.util.OracleUtil;

import java.util.ArrayList;
import java.util.List;


public class MainViewController implements ReaderListener{
    private static MainViewController instance = new MainViewController();
    private final List<Cause> causes = new ArrayList<Cause>();

    private MainView mainView;
    private PackagesReader packagesReader;

    public static synchronized MainViewController getInstance() {
        return instance;
    }

    public void showView() {
        mainView = new MainView(this);
        mainView.setVisible(true);
    }

    List<Cause> getCauses() {return causes;}

    void readCauses() {
        OracleUtil.setServerIp(mainView.getServerIp());
        packagesReader = new PackagesReader(this, mainView.getFromDate(), mainView.getToDate());
        packagesReader.readFullInSeparateThread();
    }

    void readCausesForPays() {
        OracleUtil.setServerIp(mainView.getServerIp());
        packagesReader = new PackagesReader(this, mainView.getFromDate(), mainView.getToDate());
        packagesReader.readChargeInSeparateThread();
    }

    @Override
    public void readComplete() {
       causes.clear();
        for (org.kesler.pkpvdimport.domain.Package aPackage: packagesReader.getPackages()) {
            causes.addAll(aPackage.getCauses());
        }
        mainView.updateCauses();
    }

    void exportPays() {
        XLSXExporterFactory.createReestrExporter(XLSXExportEnum.FOR_ARCHIVE).export(packagesReader.getPackages());
    }
}
