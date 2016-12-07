package com.piramide.accounting.ejb.service;

import com.piramide.Constants;
import com.piramide.accounting.AccountingUtils;
import com.piramide.accounting.ejb.domain.AccountingclosehistoryEntityLocal;
import com.piramide.accounting.ejb.domain.AccountingclosehistoryEntityLocalHome;
import com.piramide.accounting.ejb.domain.AccountinghistoryEntityLocal;
import com.piramide.accounting.ejb.domain.AccountinghistoryEntityLocalHome;
import com.piramide.accounting.ejb.domain.AccountingperiodEntityLocalHome;
import com.piramide.accounting.ejb.dto.AccountingPeriodDTO;
import com.piramide.accounting.ejb.dto.BankingConcilationDTO;
import com.piramide.accounting.ejb.dto.BookEntryDTO;
import com.piramide.accounting.ejb.dto.CheckBookDTO;
import com.piramide.accounting.ejb.dto.CheckBookDetailDTO;
import com.piramide.accounting.ejb.dto.CurrencyDTO;
import com.piramide.accounting.ejb.dto.UploadBookEntryDTO;
import com.piramide.accounting.ejb.report.AccountMoveReportDataSource;
import com.piramide.accounting.ejb.report.AccountStatusReport2DataSource;
import com.piramide.accounting.ejb.report.AccountingDataSource;
import com.piramide.accounting.ejb.report.AccountingMonitorReportDataSource;
import com.piramide.accounting.ejb.report.AccumulatedBalanceReportDataSource;
import com.piramide.accounting.ejb.report.ArchingReportDataSource;
import com.piramide.accounting.ejb.report.AuxiliaryReportDS;
import com.piramide.accounting.ejb.report.BalanceSumCheckupReportDataSource;
import com.piramide.accounting.ejb.report.BancarizationReportDataSource;
import com.piramide.accounting.ejb.report.BancarizationSalesReportDataSource;
import com.piramide.accounting.ejb.report.BookEntryListReportDataSource;
import com.piramide.accounting.ejb.report.BookEntryPreviewReportDS;
import com.piramide.accounting.ejb.report.BookEntryReportDataSource;
import com.piramide.accounting.ejb.report.EvilSeatReportDataSource;
import com.piramide.accounting.ejb.report.ExchangeTypeReportDataSource;
import com.piramide.accounting.ejb.report.GeneralBalanceByCostCentersReportDataSource;
import com.piramide.accounting.ejb.report.GeneralBalanceByMonthReportDataSource;
import com.piramide.accounting.ejb.report.GeneralBalanceReportDataSource;
import com.piramide.accounting.ejb.report.IvaPurchaseBookReportDataSource;
import com.piramide.accounting.ejb.report.LedgerChartAccountListReportDataSource;
import com.piramide.accounting.ejb.report.MajorBookReportDataSource;
import com.piramide.accounting.ejb.report.MonthBalanceReportDataSource;
import com.piramide.accounting.ejb.report.ResultStatusReportDataSource;
import com.piramide.accounting.ejb.report.UploadBookEntryReportDataSource;
import com.piramide.accounting.ejb.report.VerifyAccountingInventoryReportDataSource;
import com.piramide.accounting.ejb.report.WorkPaperReportDataSource;
import com.piramide.addressmanager.ejb.dto.Address;
import com.piramide.addressmanager.ejb.service.AddressServiceLocal;
import com.piramide.addressmanager.ejb.service.AddressServiceLocalHome;
import com.piramide.common.ejb.util.ElwisLogger;
import com.piramide.common.ejb.util.UtilsServer;
import com.piramide.inti.ejb.service.DocumentServiceLocal;
import com.piramide.inti.ejb.service.DocumentServiceLocalHome;
import com.piramide.inti.ejb.service.EJBHomeFactory;
import com.piramide.receivable.ejb.dto.ReceiveDTO;
import com.piramide.sale.ejb.report.SaleDataSource;
import com.piramide.sale.ejb.service.SaleServiceLocal;
import com.piramide.sale.ejb.service.SaleServiceLocalHome;
import com.piramide.systemconfiguration.addressmanager.ejb.dto.Currency;
import com.piramide.systemconfiguration.addressmanager.ejb.service.AddressCatalogServicesLocal;
import com.piramide.systemconfiguration.addressmanager.ejb.service.AddressCatalogServicesLocalHome;
import com.piramide.systemconfiguration.addressmanager.ejb.service.SystemSettingsServicesLocal;
import com.piramide.systemconfiguration.addressmanager.ejb.service.SystemSettingsServicesLocalHome;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.validator.GenericValidator;

public class AccountingReportServiceBean
  implements SessionBean
{
  public void ejbCreate()
    throws CreateException
  {}
  
  public void ejbActivate()
    throws EJBException
  {}
  
  public void ejbPassivate()
    throws EJBException
  {}
  
  public void ejbRemove()
    throws EJBException
  {}
  
  public void setSessionContext(SessionContext sessionContext)
    throws EJBException
  {}
  
  public HashMap ledgerChartAccountListReport(HashMap hash)
    throws Exception
  {
    hash.put("p_datasource", "1");
    LedgerChartAccountListReportDataSource major = new LedgerChartAccountListReportDataSource(hash);
    hash.put("datasourceclass", major);
    
    hash.put("datasourceclass", major);
    return hash;
  }
  
  public byte[] costCenterListReport(HashMap hash)
    throws Exception
  {
    hash.put("p_datasource", "0");
    return UtilsServer.generateReport(hash);
  }
  
  public HashMap dailyBookReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: LIBRO DIARIO ");
    ElwisLogger.message("================================================================");
    
    dth.put("p_accountNumber_start", "0");
    dth.put("p_type", "dailyBookReport");
    dth.put("p_accountNumber_end", "999999999999999");
    String costcenter_label = "%";
    dth.put("p_costcenter_label", costcenter_label);
    BookEntryListReportDataSource bookList = new BookEntryListReportDataSource(dth);
    dth.put("datasourceclass", bookList);
    
    return dth;
  }
  
  public HashMap majorBookReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: LIBRO MAYOR ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
    SystemSettingsServicesLocal service = home.create();
    String endDate = dth.get("p_date_end").toString();
    String tc = dth.get("p_currencyid_to").toString();
    double exchange;
    try
    {
      exchange = service.dateExchange(Integer.valueOf(endDate), Integer.valueOf(fromCurrency), Integer.valueOf(tc)).doubleValue();
    }
    catch (Exception ex)
    {
      double exchange;
      exchange = service.findLastDateExchange(Integer.valueOf(endDate), Integer.valueOf(fromCurrency), Integer.valueOf(tc), new Integer(30)).doubleValue();
    }
    dth.put("p_traderate", Double.toString(exchange));
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    MajorBookReportDataSource major = new MajorBookReportDataSource(dth);
    dth.put("datasourceclass", major);
    
    String fileJasper = (String)dth.get("p_jasperroot") + dth.get("p_reportid") + ".jasper";
    JasperReport jasperReport = (JasperReport)JRLoader.loadObject(fileJasper);
    JRGroup[] groups = jasperReport.getGroups();
    JRGroup group = groups[0];
    boolean onePageforAccount = false;
    if (dth.get("p_account_page") != null) {
      if (dth.get("p_account_page").toString().equals("1")) {
        onePageforAccount = true;
      }
    }
    group.setStartNewPage(onePageforAccount);
    dth.put("jasper_is_modified", Boolean.valueOf(true));
    dth.put("jasperdesign_update", jasperReport);
    
    return dth;
  }
  
  public HashMap balanceSumCheckupReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: COMPROBACION DE SUMAS Y SALDOS ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String startDate = dth.get("p_date_start").toString();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    dth.put("p_period_start", period.getStartDate());
    dth.put("p_period_id", period.getAccountingPeriodId());
    
    AccountingPeriodDTO periodCurrent = accountingService.getCurrentAccountingPeriodByDate(UtilsServer.getCurrentDate(), Integer.valueOf(companyid));
    if (period.getAccountingPeriodId().equals(periodCurrent.getAccountingPeriodId())) {
      dth.put("auxiliaryStatusPeriod", "1");
    } else {
      dth.put("auxiliaryStatusPeriod", "0");
    }
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    String dateCurrent = dth.get("p_current_date").toString();
    SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
    SystemSettingsServicesLocal service = home.create();
    try
    {
      String tc = toCurrency;
      Iterator iterator;
      if (toCurrency.equals("all"))
      {
        ArrayList currencyList = service.getTransactionCurrencies(new Integer(companyid));
        for (iterator = currencyList.iterator(); iterator.hasNext();)
        {
          CurrencyDTO cDTO = (CurrencyDTO)iterator.next();
          if ((cDTO.getIsBasicCurrency() != null) && (cDTO.getIsBasicCurrency().equals("0"))) {
            tc = cDTO.getCurrencyId();
          }
        }
      }
      double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(tc)).doubleValue();
      ElwisLogger.message("tipo de cambio " + exchange);
      dth.put("p_traderate", Double.toString(exchange));
    }
    catch (Exception ex)
    {
      dth.put("p_traderate", null);
    }
    BalanceSumCheckupReportDataSource bs = new BalanceSumCheckupReportDataSource(dth);
    dth.put("datasourceclass", bs);
    
    return dth;
  }
  
  public HashMap generalBalanceReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: BALANCE GENERAL ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String startDate = dth.get("p_date_start").toString();
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    
    AccountingPeriodDTO periodCurrent = accountingService.getCurrentAccountingPeriodByDate(UtilsServer.getCurrentDate(), Integer.valueOf(companyid));
    if (period.getAccountingPeriodId().equals(periodCurrent.getAccountingPeriodId())) {
      dth.put("auxiliaryStatusPeriod", "1");
    } else {
      dth.put("auxiliaryStatusPeriod", "0");
    }
    dth.put("p_period_start", period.getStartDate());
    dth.put("p_period_id", period.getAccountingPeriodId());
    
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    String dateCurrent = dth.get("p_current_date").toString();
    SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
    SystemSettingsServicesLocal service = home.create();
    try
    {
      String tc = toCurrency;
      Iterator iterator;
      if (toCurrency.equals("all"))
      {
        ArrayList currencyList = service.getTransactionCurrencies(new Integer(companyid));
        for (iterator = currencyList.iterator(); iterator.hasNext();)
        {
          CurrencyDTO cDTO = (CurrencyDTO)iterator.next();
          if ((cDTO.getIsBasicCurrency() != null) && (cDTO.getIsBasicCurrency().equals("0"))) {
            tc = cDTO.getCurrencyId();
          }
        }
      }
      double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(tc)).doubleValue();
      dth.put("p_traderate", Double.toString(exchange));
    }
    catch (Exception ex)
    {
      dth.put("p_traderate", null);
    }
    GeneralBalanceReportDataSource general = new GeneralBalanceReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    return dth;
  }
  
  public HashMap generalBalanceRevertReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: BALANCE GENERAL REVERT ");
    ElwisLogger.message("================================================================");
    
    AccountingclosehistoryEntityLocalHome historyHome = (AccountingclosehistoryEntityLocalHome)EJBHomeFactory.getInstance().getHome("AccountingclosehistoryEntityLocal");
    Integer id = new Integer(dth.get("p_historylabel").toString());
    Integer companyid = new Integer(dth.get("p_companyid").toString());
    AccountingclosehistoryEntityLocal historyEntity = historyHome.findByDescription(id, dth.get("p_historydesc").toString(), companyid);
    dth.put("p_history_id", historyEntity.getId());
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    int p_currencyid = saleService.getPCurrencyid(companyid);
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(historyEntity.getClosedate().intValue(), companyid);
    
    dth.put("p_period_start", period.getStartDate());
    dth.put("p_period_id", period.getAccountingPeriodId());
    
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    String dateCurrent = dth.get("p_current_date").toString();
    SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
    SystemSettingsServicesLocal service = home.create();
    try
    {
      String tc = toCurrency;
      Iterator iterator;
      if (toCurrency.equals("all"))
      {
        ArrayList currencyList = service.getTransactionCurrencies(companyid);
        for (iterator = currencyList.iterator(); iterator.hasNext();)
        {
          CurrencyDTO cDTO = (CurrencyDTO)iterator.next();
          if ((cDTO.getIsBasicCurrency() != null) && (cDTO.getIsBasicCurrency().equals("0"))) {
            tc = cDTO.getCurrencyId();
          }
        }
      }
      double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(tc)).doubleValue();
      dth.put("p_traderate", Double.toString(exchange));
    }
    catch (Exception ex)
    {
      dth.put("p_traderate", null);
    }
    GeneralBalanceReportDataSource general = new GeneralBalanceReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    return dth;
  }
  
  public HashMap resultStatusRevertReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: RESULT STATUS REVERT ");
    ElwisLogger.message("================================================================");
    
    AccountingclosehistoryEntityLocalHome historyHome = (AccountingclosehistoryEntityLocalHome)EJBHomeFactory.getInstance().getHome("AccountingclosehistoryEntityLocal");
    Integer id = new Integer(dth.get("p_historylabel").toString());
    Integer companyid = new Integer(dth.get("p_companyid").toString());
    AccountingclosehistoryEntityLocal historyEntity = historyHome.findByDescription(id, dth.get("p_historydesc").toString(), companyid);
    dth.put("p_history_id", historyEntity.getId());
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    int p_currencyid = saleService.getPCurrencyid(companyid);
    
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(historyEntity.getClosedate().intValue(), companyid);
    dth.put("p_period_start", period.getStartDate());
    dth.put("p_period_id", period.getAccountingPeriodId());
    
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    String dateCurrent = dth.get("p_current_date").toString();
    SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
    SystemSettingsServicesLocal service = home.create();
    try
    {
      String tc = toCurrency;
      Iterator iterator;
      if (toCurrency.equals("all"))
      {
        ArrayList currencyList = service.getTransactionCurrencies(companyid);
        for (iterator = currencyList.iterator(); iterator.hasNext();)
        {
          CurrencyDTO cDTO = (CurrencyDTO)iterator.next();
          if ((cDTO.getIsBasicCurrency() != null) && (cDTO.getIsBasicCurrency().equals("0"))) {
            tc = cDTO.getCurrencyId();
          }
        }
      }
      double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(tc)).doubleValue();
      dth.put("p_traderate", Double.toString(exchange));
    }
    catch (Exception ex)
    {
      dth.put("p_traderate", null);
    }
    ResultStatusReportDataSource bs = new ResultStatusReportDataSource(dth);
    dth.put("datasourceclass", bs);
    
    return dth;
  }
  
  public byte[] resultStatusReport(HashMap hash)
    throws Exception
  {
    hash.put("p_datasource", "1");
    return UtilsServer.generateReport(hash);
  }
  
  public HashMap resultStatusReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: ESTADO DE RESULTADOS ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String startDate = dth.get("p_date_start").toString();
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    dth.put("p_period_start", period.getStartDate());
    dth.put("p_period_id", period.getAccountingPeriodId());
    
    AccountingPeriodDTO periodCurrent = accountingService.getCurrentAccountingPeriodByDate(UtilsServer.getCurrentDate(), Integer.valueOf(companyid));
    if (period.getAccountingPeriodId().equals(periodCurrent.getAccountingPeriodId())) {
      dth.put("auxiliaryStatusPeriod", "1");
    } else {
      dth.put("auxiliaryStatusPeriod", "0");
    }
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    String dateCurrent = dth.get("p_current_date").toString();
    SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
    SystemSettingsServicesLocal service = home.create();
    try
    {
      String tc = toCurrency;
      Iterator iterator;
      if (toCurrency.equals("all"))
      {
        ArrayList currencyList = service.getTransactionCurrencies(new Integer(companyid));
        for (iterator = currencyList.iterator(); iterator.hasNext();)
        {
          CurrencyDTO cDTO = (CurrencyDTO)iterator.next();
          if ((cDTO.getIsBasicCurrency() != null) && (cDTO.getIsBasicCurrency().equals("0"))) {
            tc = cDTO.getCurrencyId();
          }
        }
      }
      double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(tc)).doubleValue();
      dth.put("p_traderate", Double.toString(exchange));
    }
    catch (Exception ex)
    {
      dth.put("p_traderate", null);
    }
    ResultStatusReportDataSource bs = new ResultStatusReportDataSource(dth);
    dth.put("datasourceclass", bs);
    
    return dth;
  }
  
  public byte[] accountStatusReport(HashMap hash)
    throws Exception
  {
    hash.put("p_datasource", "1");
    return UtilsServer.generateReport(hash);
  }
  
  public HashMap accountStatusReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: ESTADO DE CUENTAS ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String startDate = dth.get("p_date_start").toString();
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    dth.put("p_period_start", period.getStartDate());
    
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    AccountStatusReport2DataSource bs = new AccountStatusReport2DataSource(dth);
    dth.put("datasourceclass", bs);
    
    String fileJasper = (String)dth.get("p_jasperroot") + dth.get("p_reportid") + ".jasper";
    JasperReport jasperReport = (JasperReport)JRLoader.loadObject(fileJasper);
    JRGroup[] groups = jasperReport.getGroups();
    JRGroup group = groups[0];
    boolean onePageforAccount = false;
    if (dth.get("p_account_page") != null) {
      if (dth.get("p_account_page").toString().equals("1")) {
        onePageforAccount = true;
      }
    }
    group.setStartNewPage(onePageforAccount);
    dth.put("jasper_is_modified", Boolean.valueOf(true));
    dth.put("jasperdesign_update", jasperReport);
    ElwisLogger.message("Grupo es::::>> " + group.getName());
    
    return dth;
  }
  
  public HashMap workPaperReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: HOJA DE TRABAJO ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    String dateCurrent = dth.get("p_current_date").toString();
    SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
    SystemSettingsServicesLocal service = home.create();
    try
    {
      String tc = toCurrency;
      Iterator iterator;
      if (toCurrency.equals("all"))
      {
        ArrayList currencyList = service.getTransactionCurrencies(new Integer(companyid));
        for (iterator = currencyList.iterator(); iterator.hasNext();)
        {
          CurrencyDTO cDTO = (CurrencyDTO)iterator.next();
          if ((cDTO.getIsBasicCurrency() != null) && (cDTO.getIsBasicCurrency().equals("0"))) {
            tc = cDTO.getCurrencyId();
          }
        }
      }
      double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(tc)).doubleValue();
      ElwisLogger.message("tipo de cambio " + exchange);
      dth.put("p_traderate", Double.toString(exchange));
    }
    catch (Exception ex)
    {
      dth.put("p_traderate", null);
    }
    WorkPaperReportDataSource paper = new WorkPaperReportDataSource(dth);
    dth.put("datasourceclass", paper);
    
    return dth;
  }
  
  public HashMap bookEntryReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: COMPROBANTE ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    if (dth.get("p_dto") != null)
    {
      ElwisLogger.debugMessage("INGRESO A LA OPCION DE MONEDA EN EL BOOKENTRY....");
      String reportCurrency = ((BookEntryDTO)dth.get("p_dto")).getReportCurrency();
      if ((reportCurrency != null) && (reportCurrency.equals("0"))) {
        dth.put("p_currencylabel", dth.get("p_currencyid_to_symbol"));
      }
    }
    BookEntryReportDataSource book = new BookEntryReportDataSource(dth);
    dth.put("datasourceclass", book);
    
    return dth;
  }
  
  public HashMap bookEntryListReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: LISTA DE COMPROBANTES ");
    ElwisLogger.message("================================================================");
    
    String option = dth.get("p_tabindex").toString();
    if (option.equals("1"))
    {
      dth.put("p_date_start", Integer.valueOf("0"));
      dth.put("p_date_end", Integer.valueOf("99999999"));
    }
    else if (option.equals("2"))
    {
      dth.put("p_accountNumber_start", "0");
      dth.put("p_accountNumber_end", "999999999999999");
    }
    dth.put("p_type", "bookeEntryListReport");
    String costcenter_label = dth.get("p_costcenter_label").toString();
    if (costcenter_label.equals(""))
    {
      ElwisLogger.message("Vaciooo>>>>>>>>>>>>>>>>>>>>");
      costcenter_label = "%";
    }
    else
    {
      costcenter_label = "%" + costcenter_label + "%";
    }
    dth.put("p_costcenter_label", costcenter_label);
    BookEntryListReportDataSource bookList = new BookEntryListReportDataSource(dth);
    dth.put("datasourceclass", bookList);
    
    return dth;
  }
  
  public HashMap monthlyBalanceReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: SALDOS MENSUAL ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String startDate = String.valueOf(UtilsServer.getCurrentDate());
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    if (period.getStartDate() != null)
    {
      dth.put("p_period_start", period.getStartDate());
      dth.put("p_period_end", period.getEndDate());
      dth.put("p_date_start", startDate);
      dth.put("p_date_end", period.getEndDate());
      
      MonthBalanceReportDataSource bs = new MonthBalanceReportDataSource(dth);
      dth.put("datasourceclass", bs);
    }
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    return dth;
  }
  
  public HashMap exchangeRateVariationReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: VARIACION DEL TIPO DE CAMBIO ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String dateCurrent = dth.get("p_current_date").toString();
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = String.valueOf(p_currencyid);
    
    SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
    SystemSettingsServicesLocal service = home.create();
    double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(toCurrency)).doubleValue();
    ElwisLogger.message("tipo de cambio " + exchange);
    dth.put("p_traderate", Double.toString(exchange));
    
    ExchangeTypeReportDataSource paper = new ExchangeTypeReportDataSource(dth);
    dth.put("datasourceclass", paper);
    
    return dth;
  }
  
  public byte[] monthlyBalanceReport(HashMap hash)
    throws Exception
  {
    hash.put("p_datasource", "1");
    return UtilsServer.generateReport(hash);
  }
  
  public byte[] accountMoveReport(HashMap hash)
    throws Exception
  {
    hash.put("p_datasource", "1");
    return UtilsServer.generateReport(hash);
  }
  
  public HashMap accountMoveReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: BALANCE MENSUAL ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String startDate = dth.get("p_date_start").toString();
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    dth.put("p_period_start", period.getStartDate());
    
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    AccountMoveReportDataSource bs = new AccountMoveReportDataSource(dth);
    dth.put("datasourceclass", bs);
    
    String fileJasper = (String)dth.get("p_jasperroot") + dth.get("p_reportid") + ".jasper";
    JasperReport jasperReport = (JasperReport)JRLoader.loadObject(fileJasper);
    JRGroup[] groups = jasperReport.getGroups();
    JRGroup group = groups[0];
    boolean onePageforAccount = false;
    if (dth.get("p_account_page") != null) {
      if (dth.get("p_account_page").toString().equals("1")) {
        onePageforAccount = true;
      }
    }
    group.setStartNewPage(onePageforAccount);
    dth.put("jasper_is_modified", Boolean.valueOf(true));
    dth.put("jasperdesign_update", jasperReport);
    ElwisLogger.message("Grupo es::::>> " + group.getName());
    
    return dth;
  }
  
  public HashMap generalBalanceByCostCentersReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: BALANCE GENERAL POR CENTROS DE COSTO ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String startDate = dth.get("p_date").toString();
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    dth.put("p_period_start", period.getStartDate());
    
    dth.put("IS_BALANCE", "1");
    
    GeneralBalanceByCostCentersReportDataSource general = new GeneralBalanceByCostCentersReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    return dth;
  }
  
  public HashMap generalBalanceByMonthReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: BALANCE GENERAL POR MESES ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String month = dth.get("p_month").toString();
    String year = dth.get("p_year").toString();
    
    String startDate = year + month + "01";
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    dth.put("p_period_start", period.getStartDate());
    dth.put("p_period_id", period.getAccountingPeriodId());
    
    dth.put("IS_BALANCE", "1");
    
    GeneralBalanceByMonthReportDataSource general = new GeneralBalanceByMonthReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    return dth;
  }
  
  public HashMap bancarizationReportParameters(HashMap dth)
    throws Exception
  {
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: AUXILIARES de BANCARIZACION ");
    ElwisLogger.message("================================================================");
    
    dth.put("p_datasource", "1");
    String companyid = dth.get("p_companyid").toString();
    
    AddressServiceLocalHome addHome = (AddressServiceLocalHome)EJBHomeFactory.getInstance().getHome("AddressServiceLocal");
    AddressServiceLocal address = addHome.create();
    Address addressCompany = address.getAddress(new Integer(companyid));
    
    dth.put("p_companyName", addressCompany.getLastName() + " " + addressCompany.getFirstName() + " " + addressCompany.getName3());
    dth.put("p_nit", addressCompany.getTaxcode());
    dth.put("p_address", addressCompany.getStreet() + " " + addressCompany.getStreetnr());
    
    BancarizationReportDataSource general = new BancarizationReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    return dth;
  }
  
  public HashMap bancarizationSalesReportParameters(HashMap dth)
    throws Exception
  {
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: AUXILIAR de VENTAS - BANCARIZACION ");
    ElwisLogger.message("================================================================");
    
    dth.put("p_datasource", "1");
    String companyid = dth.get("p_companyid").toString();
    
    AddressServiceLocalHome addHome = (AddressServiceLocalHome)EJBHomeFactory.getInstance().getHome("AddressServiceLocal");
    AddressServiceLocal address = addHome.create();
    Address addressCompany = address.getAddress(new Integer(companyid));
    
    dth.put("p_companyName", addressCompany.getLastName() + " " + addressCompany.getFirstName() + " " + addressCompany.getName3());
    dth.put("p_nit", addressCompany.getTaxcode());
    dth.put("p_address", addressCompany.getStreet() + " " + addressCompany.getStreetnr());
    
    BancarizationSalesReportDataSource general = new BancarizationSalesReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    return dth;
  }
  
  public HashMap resultStatusByCostCentersReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: ESTADO DE RESULTADOS POR CENTROS DE COSTO ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String startDate = dth.get("p_date").toString();
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    dth.put("p_period_start", period.getStartDate());
    
    dth.put("p_period_id", period.getAccountingPeriodId());
    
    dth.put("IS_BALANCE", "0");
    
    GeneralBalanceByCostCentersReportDataSource general = new GeneralBalanceByCostCentersReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    return dth;
  }
  
  public HashMap resultStatusByMonthReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: ESTADO DE RESULTADOS POR MESES ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    String month = dth.get("p_month").toString();
    String year = dth.get("p_year").toString();
    
    String startDate = year + month + "01";
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(startDate), Integer.valueOf(companyid));
    dth.put("p_period_start", period.getStartDate());
    
    dth.put("p_period_id", period.getAccountingPeriodId());
    
    dth.put("IS_BALANCE", "0");
    
    GeneralBalanceByMonthReportDataSource general = new GeneralBalanceByMonthReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    return dth;
  }
  
  public HashMap ivaPurchaseBookReportParameters(HashMap dth)
    throws Exception
  {
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: LIBRO DE COMPRAS IVA ");
    ElwisLogger.message("================================================================");
    
    dth.put("p_datasource", "1");
    String companyid = dth.get("p_companyid").toString();
    
    AddressServiceLocalHome addHome = (AddressServiceLocalHome)EJBHomeFactory.getInstance().getHome("AddressServiceLocal");
    AddressServiceLocal address = addHome.create();
    Address addressCompany = address.getAddress(new Integer(companyid));
    
    dth.put("p_companyName", addressCompany.getLastName() + " " + addressCompany.getFirstName() + " " + addressCompany.getName3());
    dth.put("p_nit", addressCompany.getTaxcode());
    dth.put("p_address", addressCompany.getStreet() + " " + addressCompany.getStreetnr());
    
    IvaPurchaseBookReportDataSource general = new IvaPurchaseBookReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    return dth;
  }
  
  public HashMap checkBookReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "0");
    CheckBookDTO checkdto = (CheckBookDTO)dth.get("p_dto");
    
    String fileJasper = (String)dth.get("p_jasperroot") + dth.get("p_reportid") + ".jasper";
    JasperReport jasperReport = (JasperReport)JRLoader.loadObject(fileJasper);
    
    JRBand container = jasperReport.getPageHeader();
    JRElement[] elements = container.getElements();
    ArrayList fields = checkdto.getCheckBookDetail();
    for (int i = 0; i < fields.size(); i++)
    {
      CheckBookDetailDTO detail = (CheckBookDetailDTO)fields.get(i);
      dth.put("Field" + i, detail.getDescription());
      ElwisLogger.debugMessage("EL CAMPO ES = " + dth.get(new StringBuffer().append("Field").append(i).toString()).toString());
      
      JRTextField textField = (JRTextField)elements[i];
      textField.setX(Integer.parseInt(detail.getAxisx()));
      
      textField.setWidth(Integer.parseInt(detail.getWidth()));
    }
    dth.put("jasper_is_modified", Boolean.valueOf(true));
    dth.put("jasperdesign_update", jasperReport);
    
    return dth;
  }
  
  public HashMap uploadBookEntryReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    UploadBookEntryDTO dto = (UploadBookEntryDTO)dth.get("p_dto");
    UploadBookEntryReportDataSource book = new UploadBookEntryReportDataSource(dth);
    dth.put("datasourceclass", book);
    if (!"0".equals(dto.getStatus()))
    {
      dth.remove("p_reportid");
      dth.put("p_reportid", "uploadBookEntryListReport");
    }
    return dth;
  }
  
  public HashMap bookEntryPaymentReportParameters(HashMap dth)
    throws Exception
  {
    ElwisLogger.message("REPORT: bookEntryPaymentReportParameters");
    
    dth.put("p_datasource", "1");
    BookEntryDTO dto = (BookEntryDTO)dth.get("p_dto");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    dth.remove("p_reportid");
    dth.put("p_reportid", "bookEntryReport");
    
    String sourceTransactionId = dto.getSourceTransactionId();
    ElwisLogger.debugMessage("### BOOKENTRY PAYMENT: PAYMENT_ID = " + sourceTransactionId);
    if (sourceTransactionId != null)
    {
      AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
      AccountingServiceLocal accountingService = accountingHome.create();
      try
      {
        BookEntryDTO newBookEntryDTO = accountingService.getBookEntryBySourceIdAndDocumentId(Integer.valueOf(sourceTransactionId), Integer.valueOf(dto.getDocumentId()), Integer.valueOf(companyid));
        if (newBookEntryDTO != null)
        {
          dth.put("p_bookType", newBookEntryDTO.getTransactionTypeId());
          dth.put("p_bookNumber", newBookEntryDTO.getBookEntryLabel());
          
          dto.setAccountingPeriodId(newBookEntryDTO.getAccountingPeriodId());
          dth.put("p_dto", dto);
        }
        else
        {
          dth.remove("p_reportid");
          dth.put("p_reportid", "bookEntryPaymentReport");
          dth.put("p_bookentry_date", dto.getDate());
          dth.put("p_costcenter_label", dto.getCostCenterLabel());
          dth.put("p_costcentre_Name", dto.getCostCenterName());
          dth.put("p_bookentry_meno", dto.getMemo());
          dth.put("p_ispayment", "1");
          
          String dateCurrent = dth.get("p_current_date").toString();
          String fromCurrency = dth.get("p_basiccurrencyid").toString();
          String toCurrency = String.valueOf(p_currencyid);
          
          SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
          SystemSettingsServicesLocal service = home.create();
          double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(toCurrency)).doubleValue();
          ElwisLogger.debugMessage("tipo de cambio " + exchange);
          dth.put("p_traderate", Double.toString(exchange));
          
          dth.put("p_array_name", "bookEntryDetail");
          AccountingDataSource accountingDS = new AccountingDataSource(dth);
          dth.put("datasourceclass", accountingDS);
          return dth;
        }
      }
      catch (Exception e)
      {
        ElwisLogger.message(e.getMessage());
      }
    }
    BookEntryReportDataSource book = new BookEntryReportDataSource(dth);
    dth.put("datasourceclass", book);
    
    return dth;
  }
  
  public HashMap auxiliaryReportParameters(HashMap dth)
    throws Exception
  {
    ElwisLogger.debugMessage("### ENTRO A auxiliaryReportParameters........");
    dth.put("p_datasource", "1");
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    String companyid = dth.get("p_companyid").toString();
    AccountingPeriodDTO period = accountingService.getAccountingPeriod(String.valueOf(dth.get("p_year_1")), Integer.valueOf(companyid));
    dth.put("p_period_id", period.getAccountingPeriodId());
    AccountingPeriodDTO periodCurrent = accountingService.getCurrentAccountingPeriodByDate(UtilsServer.getCurrentDate(), Integer.valueOf(companyid));
    if (period.getAccountingPeriodId().equals(periodCurrent.getAccountingPeriodId())) {
      dth.put("auxiliaryStatusPeriod", "1");
    } else {
      dth.put("auxiliaryStatusPeriod", "0");
    }
    AuxiliaryReportDS auxiliary = new AuxiliaryReportDS(dth);
    dth.put("datasourceclass", auxiliary);
    return dth;
  }
  
  public HashMap bookEntryPreviewReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" REPORT: PREVIEW BOOKENTRY......");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    
    BookEntryDTO bookEntryDTO = (BookEntryDTO)dth.get("p_dto");
    String dateCurrent = bookEntryDTO.getDate();
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = String.valueOf(p_currencyid);
    SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
    SystemSettingsServicesLocal service = home.create();
    double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(toCurrency)).doubleValue();
    dth.put("p_traderate", Double.toString(exchange));
    if (dth.get("p_dto") != null)
    {
      ElwisLogger.debugMessage("INGRESO A LA OPCION DE MONEDA EN EL BOOKENTRY....");
      String reportCurrency = ((BookEntryDTO)dth.get("p_dto")).getReportCurrency();
      if ((reportCurrency != null) && (reportCurrency.equals("0"))) {
        dth.put("p_currencylabel", dth.get("p_currencyid_to_symbol"));
      }
    }
    BookEntryPreviewReportDS bookEntryDS = new BookEntryPreviewReportDS(dth);
    dth.put("datasourceclass", bookEntryDS);
    return dth;
  }
  
  public HashMap bookEntryReceiveReportParameters(HashMap dth)
    throws Exception
  {
    ElwisLogger.message("REPORT: bookEntryReceiveReportParameters");
    dth.put("p_datasource", "1");
    
    DocumentServiceLocalHome documentHome = (DocumentServiceLocalHome)EJBHomeFactory.getInstance().getHome("DocumentServiceLocal");
    DocumentServiceLocal documentService = documentHome.create();
    ReceiveDTO receiveDTO = (ReceiveDTO)dth.get("p_dto");
    HashMap hash = documentService.transformDTO(receiveDTO);
    BookEntryDTO bookEntryDTO = (BookEntryDTO)hash.get("transformedDTO");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    dth.remove("p_reportid");
    dth.put("p_reportid", "bookEntryReport");
    
    String sourceTransactionId = receiveDTO.getReceiveId();
    String documentId = bookEntryDTO.getDocumentId();
    ElwisLogger.debugMessage("### BOOKENTRY RECIEVE: RECEIVE_ID= " + sourceTransactionId + " DOCUMENT_ID = " + documentId);
    if (sourceTransactionId != null)
    {
      AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
      AccountingServiceLocal accountingService = accountingHome.create();
      try
      {
        BookEntryDTO newBookEntryDTO = accountingService.getBookEntryBySourceIdAndDocumentId(Integer.valueOf(sourceTransactionId), Integer.valueOf(documentId), Integer.valueOf(companyid));
        if (newBookEntryDTO != null)
        {
          dth.put("p_bookType", newBookEntryDTO.getTransactionTypeId());
          dth.put("p_bookNumber", newBookEntryDTO.getBookEntryLabel());
          bookEntryDTO.setAccountingPeriodId(newBookEntryDTO.getAccountingPeriodId());
          dth.put("p_dto", bookEntryDTO);
        }
        else
        {
          dth.remove("p_reportid");
          dth.put("p_reportid", "bookEntryPaymentReport");
          dth.put("p_bookentry_date", bookEntryDTO.getDate());
          dth.put("p_costcenter_label", bookEntryDTO.getCostCenterLabel());
          dth.put("p_costcentre_Name", bookEntryDTO.getCostCenterName());
          dth.put("p_bookentry_meno", bookEntryDTO.getMemo());
          dth.put("p_ispayment", "0");
          
          dth.remove("p_dto");
          dth.put("p_dto", bookEntryDTO);
          
          String dateCurrent = dth.get("p_current_date").toString();
          String fromCurrency = dth.get("p_basiccurrencyid").toString();
          String toCurrency = String.valueOf(p_currencyid);
          
          SystemSettingsServicesLocalHome home = (SystemSettingsServicesLocalHome)EJBHomeFactory.getInstance().getHome("SystemSettingsServicesLocal");
          SystemSettingsServicesLocal service = home.create();
          double exchange = service.dateExchange(Integer.valueOf(dateCurrent), Integer.valueOf(fromCurrency), Integer.valueOf(toCurrency)).doubleValue();
          ElwisLogger.debugMessage("tipo de cambio " + exchange);
          dth.put("p_traderate", Double.toString(exchange));
          
          dth.put("p_array_name", "bookEntryDetail");
          AccountingDataSource accountingDS = new AccountingDataSource(dth);
          dth.put("datasourceclass", accountingDS);
          return dth;
        }
      }
      catch (Exception e)
      {
        ElwisLogger.message(e.getMessage());
      }
    }
    BookEntryReportDataSource book = new BookEntryReportDataSource(dth);
    dth.put("datasourceclass", book);
    
    return dth;
  }
  
  public HashMap bankingConcilationReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: CONCILIACION BANCARIA");
    ElwisLogger.message("================================================================");
    
    BankingConcilationDTO process = (BankingConcilationDTO)dth.get("p_dto");
    dth.put("p_date_month", process.getMonthName());
    dth.put("p_date_year", process.getDateYear());
    dth.put("p_bank_label", process.getBankLabel());
    dth.put("p_bank_name", process.getBankName());
    dth.put("p_bankaccount_nr", process.getBankAccountNr());
    dth.put("p_bankaccount_owner", process.getBankAccountOwner());
    dth.put("p_amounybalance_debit", process.getAmountBalanceDebit());
    dth.put("p_amounybalance_credit", process.getAmountBalanceCredit());
    dth.put("p_amounybalance_diff", process.getAmountBalanceDiff());
    dth.put("p_total_debit", process.getTotalDebit());
    dth.put("p_total_credit", process.getTotalCredit());
    dth.put("p_total_diff", process.getTotalDiff());
    dth.put("p_amountcheck", process.getAmountCheck());
    dth.put("p_amountconcilation", process.getAmountConcilation());
    dth.put("p_amountmayor", process.getAmountMayor());
    
    dth.put("p_array_name", "transactionDetail");
    SaleDataSource saleDS = new SaleDataSource(dth);
    dth.put("datasourceclass", saleDS);
    return dth;
  }
  
  public ArrayList getBalanceByAllAccounts(Connection con, String startDate, String endDate, String costCenterLabel, String costCenterChilds, String costeCenterAll, String companyId, String languageId, String accountOrder, String basicCurrencyId, String currencyId, String dayStartDate, String beforeStartDate, String accountTypes, String accountAuxiliaries, String reportType, String periodId)
    throws Exception
  {
    ArrayList accountList = new ArrayList();
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    
    String select = "select distinct lca.glaccountnumberid as accountId, lca.glaccountnumber as accountNumber, lang1.text as accountDesc, lca.accounttypeid as accountTypeId, at.accounttypelabel as accountTypeLabel, lca.glaccountnumberparentid as parentId, lca.glaccountmov as accountMove, lca.currencyid as accountCurrencyId, lca.level as level, lca.withauxiliaries as withauxiliaries ";
    
    String from = "from ledgerchartaccount lca, accountinglangtext lang1, accounttype at ";
    
    String where = "where lca.glaccountmov = '1' ";
    
    where = where + "and lca.companyid = " + companyId + " " + "and lang1.companyid = " + companyId + " " + "and lca.glaccountdescription = lang1.langtextid " + "and lang1.languageid = " + languageId + " " + "and lca.accounttypeid = at.accounttypeid ";
    if ((accountTypes != null) && (accountTypes.equals(Constants.ACCOUNTTYPES_ENTRY_DEBIT)))
    {
      where = where + "and (at.accounttypelabel = '" + Constants.ACCOUNTTYPE_ENTRY + "' or at.accounttypelabel = '" + Constants.ACCOUNTTYPE_DEBIT + "') ";
      if ((reportType != null) && (reportType.equals("2"))) {
        where = where + "and lca.glaccountnumberid not in (Select ac.lossgainaccountid From accountingconfig ac Where ac.companyid=" + companyId + ")";
      }
    }
    else if ((accountTypes != null) && (accountTypes.equals(Constants.ACCOUNTTYPES_ALL)))
    {
      where = where + "and (at.accounttypelabel = '" + Constants.ACCOUNTTYPE_ACTIVE + "' or at.accounttypelabel = '" + Constants.ACCOUNTTYPE_PASSIVE + "' or at.accounttypelabel = '" + Constants.ACCOUNTTYPE_PATRIMONY + "' or at.accounttypelabel = '" + Constants.ACCOUNTTYPE_ENTRY + "' or at.accounttypelabel = '" + Constants.ACCOUNTTYPE_DEBIT + "') ";
    }
    else
    {
      where = where + "and (at.accounttypelabel = '" + Constants.ACCOUNTTYPE_ACTIVE + "' or at.accounttypelabel = '" + Constants.ACCOUNTTYPE_PASSIVE + "' or at.accounttypelabel = '" + Constants.ACCOUNTTYPE_PATRIMONY + "') ";
    }
    if (accountOrder.equals("1")) {
      where = where + "and (at.accounttypelabel != '" + Constants.ACCOUNTTYPE_DEBITOTHERS + "' or at.accounttypelabel != '" + Constants.ACCOUNTTYPE_ENTRYOTHERS + "') ";
    }
    String orderBy = "order by lca.glaccountnumber";
    
    String sqlAccounts = select + " " + from + " " + where + " " + orderBy;
    ElwisLogger.debugMessage("SQL PRINCIPAL PARA FILTRAR LAS CUENTAS = " + sqlAccounts);
    Statement stmt = con.createStatement();
    ResultSet rds = stmt.executeQuery(sqlAccounts);
    
    HashMap idsOfBookentryClose = AccountingUtils.getBookentryInAccountingHistory(con, companyId, periodId);
    while (rds.next())
    {
      HashMap accountData = new HashMap();
      
      String accountId = rds.getString("accountId");
      
      String accountCurrencyId = rds.getString("accountCurrencyId");
      accountData.put("r_accountnumberid", accountId);
      accountData.put("r_accountnumber", rds.getString("accountNumber"));
      accountData.put("r_accountdesc", rds.getString("accountDesc"));
      accountData.put("r_accounttypeid", rds.getString("accountTypeId"));
      accountData.put("r_accounttypelabel", rds.getString("accountTypeLabel"));
      accountData.put("r_accountparentid", rds.getString("parentId"));
      accountData.put("r_accountmove", rds.getString("accountMove"));
      accountData.put("r_accountcurrencyid", accountCurrencyId);
      accountData.put("r_accountlevel", rds.getString("level"));
      
      String withauxiliaries = rds.getString("withauxiliaries");
      
      String sqlData = "select bed.bookentrynumberid, bed.amount as amountBs, ROUND((bed.amount/bed.traderate),2) as amountSus, bed.amounttype as amountType, bed.transactiontypeid as transactionType from bookentrydetail bed ";
      if (!costCenterLabel.equals("")) {
        sqlData = sqlData + ",costcenter cc ";
      }
      sqlData = sqlData + "where bed.date between '" + startDate + "' and '" + endDate + "' " + "and bed.glaccountnumberid = '" + accountId + "' ";
      if (!costCenterLabel.equals(""))
      {
        sqlData = sqlData + "and bed.costcenterid = cc.costcenterid ";
        if (costeCenterAll.equals("")) {
          sqlData = sqlData + "and cc.costcenterlabel = '" + costCenterLabel + "' ";
        } else {
          sqlData = sqlData + "and cc.costcenterlabel in " + costCenterChilds + " ";
        }
      }
      sqlData = sqlData + "and bed.preliminary = '0' and bed.companyid = " + companyId + " " + "order by bed.bookentrynumberid, bed.date";
      
      Statement st = con.createStatement();
      ResultSet rs = st.executeQuery(sqlData);
      ElwisLogger.debugMessage("SQL DATA = " + sqlData);
      
      BigDecimal amountDebitBs = new BigDecimal("0");
      BigDecimal amountDebitSus = new BigDecimal("0");
      BigDecimal amountCreditBs = new BigDecimal("0");
      BigDecimal amountCreditSus = new BigDecimal("0");
      while (rs.next())
      {
        String idsBookEntryClose = rs.getString("bookentrynumberid");
        boolean canNotContinue = false;
        if (!idsOfBookentryClose.isEmpty())
        {
          String open = idsOfBookentryClose.get("periodopenid").toString();
          String close = idsOfBookentryClose.get("periodcloseid").toString();
          if (idsBookEntryClose.equals(open)) {
            canNotContinue = true;
          } else if (idsBookEntryClose.equals(close)) {
            canNotContinue = true;
          }
        }
        if (!canNotContinue)
        {
          BigDecimal amountBs = new BigDecimal(rs.getString("amountBs"));
          BigDecimal amountSus = new BigDecimal(rs.getString("amountSus"));
          String amountType = rs.getString("amountType");
          String transactionType = rs.getString("transactionType");
          if (amountType.equals("1"))
          {
            if ((!GenericValidator.isBlankOrNull(transactionType)) && (!transactionType.equals("6"))) {
              amountDebitBs = amountDebitBs.add(amountBs);
            }
            if ((!GenericValidator.isBlankOrNull(transactionType)) && (!transactionType.equals("5")) && (!transactionType.equals("7"))) {
              amountDebitSus = amountDebitSus.add(amountSus);
            }
          }
          else if (amountType.equals("2"))
          {
            if ((!GenericValidator.isBlankOrNull(transactionType)) && (!transactionType.equals("6"))) {
              amountCreditBs = amountCreditBs.add(amountBs);
            }
            if ((!GenericValidator.isBlankOrNull(transactionType)) && (!transactionType.equals("5")) && (!transactionType.equals("7"))) {
              amountCreditSus = amountCreditSus.add(amountSus);
            }
          }
        }
      }
      rs.close();
      st.close();
      
      accountData.put("r_debitAccumER", amountDebitBs.setScale(2, 4).toString());
      accountData.put("r_debitsusAccumER", amountDebitSus.setScale(2, 4).toString());
      accountData.put("r_creditAccumER", amountCreditBs.setScale(2, 4).toString());
      accountData.put("r_creditsusAccumER", amountCreditSus.setScale(2, 4).toString());
      if ((reportType != null) && (reportType.equals("2")))
      {
        String sqlRS = " select amounttype, transactiontypeid, SUM(amount) amountBs,  SUM(ROUND((amount/traderate),2)) amountSus  from balanceresult  where adjustmentdate = " + endDate + " and companyid = " + companyId + " and accountid = " + accountId + " group by accountid, amounttype, transactiontypeid";
        
        ElwisLogger.debugMessage(sqlRS);
        Statement stRS = con.createStatement();
        ResultSet rsRS = stRS.executeQuery(sqlRS);
        while (rsRS.next())
        {
          BigDecimal amountBsRS = new BigDecimal(rsRS.getString("amountBs"));
          BigDecimal amountSusRS = new BigDecimal(rsRS.getString("amountSus"));
          String amountTypeRS = rsRS.getString("amounttype");
          String transactionTypeRS = rsRS.getString("transactiontypeid");
          if (amountTypeRS.equals("1"))
          {
            if ((!GenericValidator.isBlankOrNull(transactionTypeRS)) && (!transactionTypeRS.equals("6"))) {
              amountDebitBs = amountDebitBs.subtract(amountBsRS);
            }
            if ((!GenericValidator.isBlankOrNull(transactionTypeRS)) && (!transactionTypeRS.equals("5")) && (!transactionTypeRS.equals("7"))) {
              amountDebitSus = amountDebitSus.subtract(amountSusRS);
            }
          }
          else if (amountTypeRS.equals("2"))
          {
            if ((!GenericValidator.isBlankOrNull(transactionTypeRS)) && (!transactionTypeRS.equals("6"))) {
              amountCreditBs = amountCreditBs.subtract(amountBsRS);
            }
            if ((!GenericValidator.isBlankOrNull(transactionTypeRS)) && (!transactionTypeRS.equals("5")) && (!transactionTypeRS.equals("7"))) {
              amountCreditSus = amountCreditSus.subtract(amountSusRS);
            }
          }
        }
      }
      accountData.put("r_debit", amountDebitBs.setScale(2, 4).toString());
      accountData.put("r_debitsus", amountDebitSus.setScale(2, 4).toString());
      accountData.put("r_credit", amountCreditBs.setScale(2, 4).toString());
      accountData.put("r_creditsus", amountCreditSus.setScale(2, 4).toString());
      
      HashMap balanceAccount = accountingService.getBalanceByAccount(accountId, dayStartDate, beforeStartDate, companyId, basicCurrencyId, currencyId, costeCenterAll, costCenterLabel, costCenterChilds, false, "", idsOfBookentryClose, withauxiliaries);
      
      BigDecimal debitAcc = new BigDecimal("0");
      BigDecimal creditAcc = new BigDecimal("0");
      BigDecimal debitsusAcc = new BigDecimal("0");
      BigDecimal creditsusAcc = new BigDecimal("0");
      if (!balanceAccount.isEmpty())
      {
        debitAcc = (BigDecimal)balanceAccount.get("debitBs");
        creditAcc = (BigDecimal)balanceAccount.get("creditBs");
        debitsusAcc = (BigDecimal)balanceAccount.get("debitSus");
        creditsusAcc = (BigDecimal)balanceAccount.get("creditSus");
      }
      accountData.put("r_balamount_debit", debitAcc.toString());
      accountData.put("r_balamountsus_debit", debitsusAcc.toString());
      accountData.put("r_balamount_credit", creditAcc.toString());
      accountData.put("r_balamountsus_credit", creditsusAcc.toString());
      
      accountList.add(accountData);
      if ((accountAuxiliaries != null) && (accountAuxiliaries.equals("1"))) {
        if (withauxiliaries.equals("1")) {
          accountList = getAuxiliaries(accountId, accountList, con, languageId, accountingService, currencyId, basicCurrencyId, dayStartDate, beforeStartDate, companyId, startDate, endDate, reportType, idsOfBookentryClose, costeCenterAll, costCenterLabel, costCenterChilds);
        }
      }
    }
    rds.close();
    stmt.close();
    
    return accountList;
  }
  
  private ArrayList getAuxiliaries(String accountId, ArrayList accountList, Connection con, String languageId, AccountingServiceLocal accountingService, String currencyId, String basicCurrencyId, String dayStartDate, String beforeStartDate, String companyId, String startDate, String endDate, String reportType, HashMap idsOfBookentryClose, String costeCenterAll, String costCenterLabel, String costCenterChilds)
  {
    try
    {
      Statement aStmt = con.createStatement();
      String sql = "select ac.auxiliaryaccountlabel as auxiliaryLabel,ac.auxiliaryaccountid as auxiliaryAccountId, ac.supplierid as vendorId,v.vendorlabel as vendorLabel, av.name1 as vendorLastName,av.name2 as vendorFirstName,ac.customerid as customerId,cc.customercontractlabel as customerLabel, acc.name1 as customerLastName,acc.name2 as customerFirstName,ac.warehouseid as warehouseId,w.warehouselabel as warehouseLabel, il.text as warehouseDesc, ac.bankaccountid as bankAccountId,b.bankcode as bankLabel, bc.accountno as bankAccountNumber,b.bankname as bankName,ac.groupaccountid as productGroupId,pg.productgrouplabel as productGroupLabel, ilpg.text as productGroupDesc,ac.addressid as contactId,aux.addressid as contactLabel, aux.name1 as contactLastName,aux.name2 as contactFirstName, empl.employeeid as employeeId, empl.nickname as employeeLabel, ae.name1 as employeeLastName, ae.name2 as employeeFirstName from auxiliarybyaccount aba,auxiliaryaccount ac left join vendor v on(ac.supplierid = v.supplierid) left join address av on(v.addressid = av.addressid) left join customercontract cc on(ac.customerid = cc.customerid) left join address acc on(acc.addressid = cc.addressid) left join warehouse w on(ac.warehouseid = w.warehouseid) left join inventorylangtext il on(w.warehousedesc = il.langtextid and il.languageid = '" + languageId + "') " + "left join bankaccount bc on(ac.bankaccountid = bc.bankaccountid) " + "left join bank b on(bc.bankid = b.bankid) " + "left join productgroup pg on(ac.groupaccountid = pg.productgroupid) " + "left join inventorylangtext ilpg on(pg.productiongroupdesc = ilpg.langtextid and ilpg.languageid = '" + languageId + "') " + "left join address aux on(ac.addressid = aux.addressid) " + "left join employee empl on (ac.employeeid = empl.employeeid) " + "left join address ae on (ae.addressid=empl.addressid) " + "where aba.glaccountnumberid = '" + accountId + "' " + "and aba.auxiliaryaccountid = ac.auxiliaryaccountid " + "order by ac.auxiliaryaccountlabel";
      
      ResultSet aRds = aStmt.executeQuery(sql);
      while (aRds.next())
      {
        String auxiliaryAccountId = aRds.getString("auxiliaryAccountId");
        String auxiliaryLabel = aRds.getString("auxiliaryLabel");
        String accountname = auxiliaryLabel + " - ";
        
        Object auxiliary = aRds.getObject("vendorId");
        if (auxiliary != null) {
          if (!aRds.getString("vendorFirstName").equals("")) {
            accountname = accountname + aRds.getString("vendorLabel") + " - " + aRds.getString("vendorLastName") + ", " + aRds.getString("vendorFirstName");
          } else {
            accountname = accountname + aRds.getString("vendorLabel") + " - " + aRds.getString("vendorLastName");
          }
        }
        auxiliary = aRds.getObject("customerId");
        if (auxiliary != null) {
          if (!aRds.getString("customerFirstName").equals("")) {
            accountname = accountname + aRds.getString("customerLabel") + " - " + aRds.getString("customerLastName") + ", " + aRds.getString("customerFirstName");
          } else {
            accountname = accountname + aRds.getString("customerLabel") + " - " + aRds.getString("customerLastName");
          }
        }
        auxiliary = aRds.getObject("employeeId");
        if (auxiliary != null) {
          if (!aRds.getString("employeeFirstName").equals("")) {
            accountname = accountname + aRds.getString("employeeLabel") + " - " + aRds.getString("employeeLastName") + ", " + aRds.getString("employeeFirstName");
          } else {
            accountname = accountname + aRds.getString("employeeLabel") + " - " + aRds.getString("employeeLastName");
          }
        }
        auxiliary = aRds.getObject("warehouseId");
        if (auxiliary != null) {
          accountname = accountname + aRds.getString("warehouseLabel") + " - " + aRds.getString("warehouseDesc");
        }
        auxiliary = aRds.getObject("bankAccountId");
        if (auxiliary != null) {
          accountname = accountname + aRds.getString("bankLabel") + " - " + aRds.getString("bankAccountNumber") + " " + aRds.getString("bankName");
        }
        auxiliary = aRds.getObject("productGroupId");
        if (auxiliary != null) {
          accountname = accountname + aRds.getString("productGroupLabel") + " - " + aRds.getString("productGroupDesc");
        }
        auxiliary = aRds.getObject("contactId");
        if (auxiliary != null) {
          if (!aRds.getString("contactFirstName").equals("")) {
            accountname = accountname + aRds.getString("contactLabel") + " - " + aRds.getString("contactLastName") + ", " + aRds.getString("contactFirstName");
          } else {
            accountname = accountname + aRds.getString("contactLabel") + " - " + aRds.getString("contactLastName");
          }
        }
        String accountTypeLabel = "";
        String accountTypeId = "";
        String level = "";
        Statement st1 = con.createStatement();
        String tSql = "Select at.accounttypeid as accountTypeId, at.accounttypelabel as accountTypeLabel, lca.level as level From accounttype at, ledgerchartaccount lca Where at.accounttypeid=lca.accounttypeid and lca.glaccountnumberid=" + accountId;
        ResultSet rs1 = st1.executeQuery(tSql);
        if (rs1.next())
        {
          accountTypeLabel = rs1.getString("accountTypeLabel");
          accountTypeId = rs1.getString("accountTypeId");
          level = rs1.getString("level");
        }
        HashMap auxiliaryData = new HashMap();
        auxiliaryData.put("r_accountdesc", accountname);
        auxiliaryData.put("r_accountnumberid", accountId + "::" + auxiliaryAccountId);
        auxiliaryData.put("r_accountnumber", "");
        auxiliaryData.put("r_accounttypeid", accountTypeId);
        auxiliaryData.put("r_accounttypelabel", accountTypeLabel);
        auxiliaryData.put("r_accountparentid", "");
        auxiliaryData.put("r_accountmove", "");
        auxiliaryData.put("r_accountcurrencyid", "");
        auxiliaryData.put("r_accountlevel", level);
        
        String sqlData = "select tba.amount as amountBs, ROUND((tba.amount/bed.traderate),2) as amountSus, tba.amounttype as amountType, bed.transactiontypeid as transactionType, bed.bookentrynumberid bookentrynumberid ";
        
        sqlData = sqlData + "from bookentrydetail bed ";
        sqlData = sqlData + ",transactionbyauxiliary tba ";
        if (!costCenterLabel.equals("")) {
          sqlData = sqlData + ",costcenter cc ";
        }
        sqlData = sqlData + "where bed.preliminary = '0' and bed.glaccountnumberid = '" + accountId + "' ";
        
        sqlData = sqlData + "and bed.date between '" + startDate + "' and '" + endDate + "' " + "and bed.companyid = '" + companyId + "' ";
        
        sqlData = sqlData + "and bed.bookentrynumberid = tba.bookentrynumberid and bed.glaccountnumberid = tba.glaccountnumberid and bed.amounttype = tba.amounttype and bed.position = tba.positiondetail and tba.auxiliaryaccountid = '" + auxiliaryAccountId + "' ";
        if (!costCenterLabel.equals(""))
        {
          sqlData = sqlData + "and bed.costcenterid = cc.costcenterid ";
          if (costeCenterAll.equals("")) {
            sqlData = sqlData + "and cc.costcenterlabel = '" + costCenterLabel + "' ";
          } else {
            sqlData = sqlData + "and cc.costcenterlabel in " + costCenterChilds + " ";
          }
        }
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sqlData);
        ElwisLogger.debugMessage("SQL DATA AUX = " + sqlData);
        
        BigDecimal amountDebitBs = new BigDecimal("0");
        BigDecimal amountDebitSus = new BigDecimal("0");
        BigDecimal amountCreditBs = new BigDecimal("0");
        BigDecimal amountCreditSus = new BigDecimal("0");
        while (rs.next())
        {
          String idsBookEntryClose = rs.getString("bookentrynumberid");
          boolean canNotContinue = false;
          if (!idsOfBookentryClose.isEmpty())
          {
            String open = idsOfBookentryClose.get("periodopenid").toString();
            String close = idsOfBookentryClose.get("periodcloseid").toString();
            if (idsBookEntryClose.equals(open)) {
              canNotContinue = true;
            } else if (idsBookEntryClose.equals(close)) {
              canNotContinue = true;
            }
          }
          if (!canNotContinue)
          {
            BigDecimal amountBs = new BigDecimal(rs.getString("amountBs"));
            BigDecimal amountSus = new BigDecimal(rs.getString("amountSus"));
            String amountType = rs.getString("amountType");
            String transactionType = rs.getString("transactionType");
            if (amountType.equals("1"))
            {
              if ((!GenericValidator.isBlankOrNull(transactionType)) && (!transactionType.equals("6"))) {
                amountDebitBs = amountDebitBs.add(amountBs);
              }
              if ((!GenericValidator.isBlankOrNull(transactionType)) && (!transactionType.equals("5")) && (!transactionType.equals("7"))) {
                amountDebitSus = amountDebitSus.add(amountSus);
              }
            }
            else if (amountType.equals("2"))
            {
              if ((!GenericValidator.isBlankOrNull(transactionType)) && (!transactionType.equals("6"))) {
                amountCreditBs = amountCreditBs.add(amountBs);
              }
              if ((!GenericValidator.isBlankOrNull(transactionType)) && (!transactionType.equals("5")) && (!transactionType.equals("7"))) {
                amountCreditSus = amountCreditSus.add(amountSus);
              }
            }
          }
        }
        rs.close();
        st.close();
        
        auxiliaryData.put("r_debitAccumER", amountDebitBs.setScale(2, 4).toString());
        auxiliaryData.put("r_debitsusAccumER", amountDebitSus.setScale(2, 4).toString());
        auxiliaryData.put("r_creditAccumER", amountCreditBs.setScale(2, 4).toString());
        auxiliaryData.put("r_creditsusAccumER", amountCreditSus.setScale(2, 4).toString());
        if ((reportType != null) && (reportType.equals("2")))
        {
          String sqlRS = " select amounttype, transactiontypeid, SUM(amount) amountBs,  SUM(ROUND((amount/traderate),2)) amountSus  from balanceresult  where adjustmentdate = " + endDate + " and companyid = " + companyId + " and accountid = " + accountId + " and auxiliaryid = " + auxiliaryAccountId + " group by accountid, auxiliaryid, amounttype, transactiontypeid";
          
          Statement stRS = con.createStatement();
          ResultSet rsRS = stRS.executeQuery(sqlRS);
          while (rsRS.next())
          {
            BigDecimal amountBsRS = new BigDecimal(rsRS.getString("amountBs"));
            BigDecimal amountSusRS = new BigDecimal(rsRS.getString("amountSus"));
            String amountTypeRS = rsRS.getString("amounttype");
            String transactionTypeRS = rsRS.getString("transactiontypeid");
            if (amountTypeRS.equals("1"))
            {
              if ((!GenericValidator.isBlankOrNull(transactionTypeRS)) && (!transactionTypeRS.equals("6"))) {
                amountDebitBs = amountDebitBs.subtract(amountBsRS);
              }
              if ((!GenericValidator.isBlankOrNull(transactionTypeRS)) && (!transactionTypeRS.equals("5")) && (!transactionTypeRS.equals("7"))) {
                amountDebitSus = amountDebitSus.subtract(amountSusRS);
              }
            }
            else if (amountTypeRS.equals("2"))
            {
              if ((!GenericValidator.isBlankOrNull(transactionTypeRS)) && (!transactionTypeRS.equals("6"))) {
                amountCreditBs = amountCreditBs.subtract(amountBsRS);
              }
              if ((!GenericValidator.isBlankOrNull(transactionTypeRS)) && (!transactionTypeRS.equals("5")) && (!transactionTypeRS.equals("7"))) {
                amountCreditSus = amountCreditSus.subtract(amountSusRS);
              }
            }
          }
        }
        auxiliaryData.put("r_debit", amountDebitBs.setScale(2, 4).toString());
        auxiliaryData.put("r_debitsus", amountDebitSus.setScale(2, 4).toString());
        auxiliaryData.put("r_credit", amountCreditBs.setScale(2, 4).toString());
        auxiliaryData.put("r_creditsus", amountCreditSus.setScale(2, 4).toString());
        
        HashMap balanceAccount = accountingService.getBalanceByAccount(accountId, dayStartDate, beforeStartDate, companyId, basicCurrencyId, currencyId, costeCenterAll, costCenterLabel, costCenterChilds, true, auxiliaryAccountId, idsOfBookentryClose, "1");
        
        BigDecimal debitAcc = new BigDecimal("0");
        BigDecimal creditAcc = new BigDecimal("0");
        BigDecimal debitsusAcc = new BigDecimal("0");
        BigDecimal creditsusAcc = new BigDecimal("0");
        if (!balanceAccount.isEmpty())
        {
          debitAcc = (BigDecimal)balanceAccount.get("debitBs");
          creditAcc = (BigDecimal)balanceAccount.get("creditBs");
          debitsusAcc = (BigDecimal)balanceAccount.get("debitSus");
          creditsusAcc = (BigDecimal)balanceAccount.get("creditSus");
        }
        auxiliaryData.put("r_balamount_debit", debitAcc.toString());
        auxiliaryData.put("r_balamountsus_debit", debitsusAcc.toString());
        auxiliaryData.put("r_balamount_credit", creditAcc.toString());
        auxiliaryData.put("r_balamountsus_credit", creditsusAcc.toString());
        accountList.add(auxiliaryData);
      }
      aRds.close();
      aStmt.close();
      return accountList;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return accountList;
  }
  
  public HashMap accumulatedBalanceReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "1");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: SALDOS ACUMULADOS ");
    ElwisLogger.message("================================================================");
    
    SaleServiceLocalHome saleServiceHome = (SaleServiceLocalHome)EJBHomeFactory.getInstance().getHome("SaleServiceLocal");
    SaleServiceLocal saleService = saleServiceHome.create();
    String companyid = dth.get("p_companyid").toString();
    int p_currencyid = saleService.getPCurrencyid(new Integer(companyid));
    dth.put("p_currencyto", String.valueOf(p_currencyid));
    
    AccountingperiodEntityLocalHome home = (AccountingperiodEntityLocalHome)EJBHomeFactory.getInstance().getHome("AccountingperiodEntityLocal");
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingLocal = accountingHome.create();
    AccountingPeriodDTO period = new AccountingPeriodDTO();
    period = accountingLocal.fillAccountingPeriodDTO(period, home.findByPeriodLabel((String)dth.get("p_period"), Integer.valueOf(companyid)));
    dth.put("p_period_start", period.getStartDate());
    dth.put("p_period_end", period.getEndDate());
    
    String fromCurrency = dth.get("p_basiccurrencyid").toString();
    String toCurrency = dth.get("p_currencyid").toString();
    String currencyType = "";
    if (!fromCurrency.equals(toCurrency)) {
      currencyType = "1";
    }
    if (toCurrency.equals("all")) {
      currencyType = "2";
    }
    if (fromCurrency.equals(toCurrency)) {
      currencyType = "0";
    }
    dth.put("p_currencytype", currencyType);
    
    AddressCatalogServicesLocalHome addressCatalogHome = (AddressCatalogServicesLocalHome)EJBHomeFactory.getInstance().getHome("AddressCatalogServicesLocal");
    AddressCatalogServicesLocal addressCatalogService = addressCatalogHome.create();
    
    String currencySymbol = addressCatalogService.getCurrency(String.valueOf(p_currencyid)).getSymbol();
    dth.put("p_nobasiccurrency_symbol", currencySymbol);
    
    AccumulatedBalanceReportDataSource general = new AccumulatedBalanceReportDataSource(dth);
    dth.put("datasourceclass", general);
    
    return dth;
  }
  
  public HashMap accountingMonitorReportParameters(HashMap dth)
    throws Exception
  {
    dth.put("p_datasource", "0");
    
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: Control de contabilidad ");
    ElwisLogger.message("================================================================");
    
    dth.put("p_datasource", "1");
    AccountingMonitorReportDataSource result = new AccountingMonitorReportDataSource(dth);
    dth.put("datasourceclass", result);
    return dth;
  }
  
  public HashMap verifyAccountingInventoryReportParameters(HashMap dth)
    throws Exception
  {
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: Control de contabilidad ");
    ElwisLogger.message("================================================================");
    
    AccountingServiceLocalHome accountingHome = (AccountingServiceLocalHome)EJBHomeFactory.getInstance().getHome("AccountingServiceLocal");
    AccountingServiceLocal accountingService = accountingHome.create();
    String endDate = dth.get("p_end_date").toString();
    String companyid = dth.get("p_companyid").toString();
    AccountingPeriodDTO period = accountingService.getCurrentAccountingPeriodByDate(Integer.parseInt(endDate), Integer.valueOf(companyid));
    dth.put("p_period_start", period.getStartDate());
    try
    {
      AccountinghistoryEntityLocalHome accountinghistoryHome = (AccountinghistoryEntityLocalHome)EJBHomeFactory.getInstance().getHome("AccountinghistoryEntityLocal");
      AccountinghistoryEntityLocal historyEntity = accountinghistoryHome.findByAccountingPeriod(new Integer(period.getAccountingPeriodId()), new Integer(companyid), "0", new Integer("3"));
      dth.put("p_bookentryid_closeperiod", historyEntity.getPeriodcloseid());
    }
    catch (Exception ex)
    {
      ElwisLogger.message(ex.getMessage());
    }
    if (dth.get("p_tabindex").equals("3"))
    {
      dth.put("p_warehousecategory_end", dth.get("p_warehousecategory_start"));
      dth.put("p_warehousecategorydesc_end", dth.get("p_warehousecategorydesc_start"));
    }
    dth.put("p_datasource", "1");
    VerifyAccountingInventoryReportDataSource result = new VerifyAccountingInventoryReportDataSource(dth);
    dth.put("p_exchange_rate", result.getTraderate());
    dth.put("datasourceclass", result);
    return dth;
  }
  
  public HashMap evilSeatReportParameters(HashMap dth)
    throws Exception
  {
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: asientos mal formados ");
    ElwisLogger.message("================================================================");
    
    dth.put("p_datasource", "1");
    EvilSeatReportDataSource result = new EvilSeatReportDataSource(dth);
    dth.put("datasourceclass", result);
    return dth;
  }
  
  public HashMap archingReportParameters(HashMap dth)
    throws Exception
  {
    ElwisLogger.message("================================================================");
    ElwisLogger.message(" Parametros para: Arqueo ");
    ElwisLogger.message("================================================================");
    dth.put("p_reportid", "archingReport");
    dth.put("p_datasource", "1");
    ArchingReportDataSource result = new ArchingReportDataSource(dth);
    dth.put("datasourceclass", result);
    return dth;
  }
}
