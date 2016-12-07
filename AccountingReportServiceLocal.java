package com.piramide.accounting.ejb.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.EJBLocalObject;

public abstract interface AccountingReportServiceLocal
  extends EJBLocalObject
{
  public abstract HashMap ledgerChartAccountListReport(HashMap paramHashMap)
    throws Exception;
  
  public abstract byte[] costCenterListReport(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap dailyBookReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap majorBookReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap balanceSumCheckupReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap generalBalanceReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap generalBalanceRevertReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap resultStatusRevertReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract byte[] resultStatusReport(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap resultStatusReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract byte[] accountStatusReport(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap accountStatusReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap workPaperReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap bookEntryReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap bookEntryListReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap monthlyBalanceReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap exchangeRateVariationReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract byte[] accountMoveReport(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap accountMoveReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap generalBalanceByCostCentersReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap generalBalanceByMonthReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap bancarizationReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap bancarizationSalesReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap resultStatusByCostCentersReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap resultStatusByMonthReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap ivaPurchaseBookReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap checkBookReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap uploadBookEntryReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap bookEntryPaymentReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap auxiliaryReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap bookEntryPreviewReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap bookEntryReceiveReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap bankingConcilationReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract ArrayList getBalanceByAllAccounts(Connection paramConnection, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16)
    throws Exception;
  
  public abstract HashMap accumulatedBalanceReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap accountingMonitorReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap verifyAccountingInventoryReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap evilSeatReportParameters(HashMap paramHashMap)
    throws Exception;
  
  public abstract HashMap archingReportParameters(HashMap paramHashMap)
    throws Exception;
}
