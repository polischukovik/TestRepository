import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.softservice.core.CustomerPerson;
import org.softservice.core.DictionaryRecord;
import org.softservice.core.SessionContext;
import org.softservice.core.SessionContext.ContextIterator;

import com.stc.util.SqlUtils;
import com.stc.util.StringUtils;
import com.stc.web.DataPager;
import com.stc.web.Page;
import com.stc.web.RequestHandler;
import com.stc.webcam.core.Criteria;
import com.stc.webcam.core.PersistentBroker;
import com.stc.webcam.core.PersistentObject;
import com.stc.webcam.exception.InternalErrorException;

public class CustomerRecord extends PersistentObject {
	
	public static final String FIELD_REGISTERED_FROM = "report_registered_from";
    public static final String FIELD_REGISTERED_TO = "report_registered_to";
    public static final String FIELD_ACCOUNT_BALANCE_FROM = "report_account_balance_from";
    public static final String FIELD_ACCOUNT_BALANCE_TO = "report_account_balance_to";
    public static final String FIELD_LAST_LOGIN_FROM = "report_last_login_from";
    public static final String FIELD_LAST_LOGIN_TO = "report_last_login_to";
    public static final String FIELD_LAST_PAYMENT_FROM = "report_last_payment_from";
    public static final String FIELD_LAST_PAYMENT_TO = "report_last_payment_to";
    public static final String FIELD_CONFIRMED = "report_confirmed";
    public static final String FIELD_LAST_IP_ADDRESS = "report_last_ip_address";
    public static final String FIELD_LANGUAGE = "report_language";
    public static final int CONFIRMED_ANY = 0;
    public static final int CONFIRMED_NOT_CONFIRMED = 1;
    public static final int CONFIRMED_CONFIRMED = 2;
    public static final int SORT_ID = 0;
    public static final int SORT_NAME = 1;
    public static final int SORT_EMAIL = 2;
    public static final int SORT_BALANCE = 3;
    public static final int SORT_SCREEN_NAME = 4;
    public static final int SORT_TOTAL_SPENT = 5;
    public static final int SORT_REGISTRATION_DATE = 6;
    public static final int SORT_LAST_LOGIN_TIME = 7;
    public static final int SORT_LAST_PAYMENT_TIME = 8;
    
    public int customerId;
    public String customerName;
    public String customerScreenName;
    public String customerEmail;
    public boolean customerEmailConfirmed;
	public Timestamp customerBirthDate;
	public String customerAstrological;
	public String customerInfo;
	public String customerChildren;
	public String customerCity;
	public String customerCountry;
	public String customerDrink;
	public String customerEyeColor;
	public String customerHairColor;
	public String customerGender;
	public String customerHeight;
	public String customerOccupation;
	public String customerReligion;
	public String customerTime_zone;
	public String customerSmoking;
	public String customerWeight;
	public boolean customerOnline;
    public static final PersistentBroker broker;
    
    static {
        broker = new PersistentBroker() {
            @Override
            protected PersistentObject materialize(final ResultSet rs) throws SQLException, InternalErrorException {
                final CustomerRecord record = new CustomerRecord();
                record.load(rs);
                return record;
            }
        };
    }
    
    public static DataPager findFromRequest(final RequestHandler req) throws InternalErrorException {
        return load(req, true, false);
    }
    
    public static DataPager export(final RequestHandler req) throws InternalErrorException {
        return load(req, false, false);            
    }
    
    private static DataPager load(final RequestHandler req, final boolean limitQuery, final boolean addToMailer) throws InternalErrorException {
        try {
            SqlUtils.beginTransaction();
            final String[] addressRange = StringUtils.getNetworkRange(req.getString("report_last_ip_address", ""));
            final long[] ip = { StringUtils.ipToLong(addressRange[0]), StringUtils.ipToLong(addressRange[1]) };
            if (ip[0] != 0L || ip[1] != 4294967295L) {
                PersistentBroker.executeQuery("drop table if exists ipReport", Criteria.EMPTY);
                PersistentBroker.executeQuery("create temporary table ipReport select l.customerId from customerLocations l", new Criteria("where l.ip>=? and l.ip<=? group by l.customerId").add(ip[0]).add(ip[1]));
                PersistentBroker.executeQuery("alter table ipReport add primary key(customerId)", Criteria.EMPTY);
            }
            //customerId
            final Criteria criteria = new Criteria("");
            if (req.getInt("customer_id", 0) > 0) {
                criteria.addCondition("j.id=?").add(req.getInt("customer_id", 0));
            }
            //customerName
            if (req.getString("customer_name", "").length() > 0) {
                criteria.addCondition("LOWER(j.name) like ?").add(String.valueOf(req.getString("customer_name", "").toLowerCase()) + "%");
            }            
            //customerScreenName
            if (req.getString("customer_screen_name", "").length() > 0) {
                criteria.addCondition("LOWER(j.customerScreenName) like ?").add(String.valueOf(req.getString("customer_screen_name", "").toLowerCase()) + "%");
            }
            //customerEmail
            if (req.getString("customer_email", "").length() > 0) {
                criteria.addCondition("LOWER(j.email) like ?").add(String.valueOf(req.getString("customer_email", "").toLowerCase()) + "%");
            }
            //customerActivationCode
            if (req.getString("customerActivationCode", "").length() > 0) {
                criteria.addCondition("LOWER(j.activationCode) like ?").add(String.valueOf(req.getString("customerActivationCode", "").toLowerCase()) + "%");
            }
			
			//customerBirthDate
			if (req.getInt("customerAgeTo", 0) > 0) {
				criteria.addCondition("birthDate > ?").add( new Date(  SqlUtils.calendarAdd(new Timestamp(System.currentTimeMillis()), Calendar.YEAR, -1*req.getInt("customerAgeTo", 0)).getTime() ));
			}
			if (req.getInt("customerAgeFrom", 0) > 0) {
				 criteria.addCondition("birthDate < ? ").add( new Date( SqlUtils.calendarAdd(new Timestamp(System.currentTimeMillis()), Calendar.YEAR, -1*req.getInt("customerAgeFrom", 0)).getTime() ));
			}                
            
          //customerAstrological
            if (req.getString("astrological", "").length() > 0) {
                criteria.addCondition("LOWER(astrological) = ?").add(String.valueOf(req.getString("astrological", "").toLowerCase()));
            }
          				
            //eyeColor
            if (req.getString("eyeColor", "").length() > 0) {
                criteria.addCondition("LOWER(eyeColor) = ?").add(String.valueOf(req.getString("eyeColor", "").toLowerCase()));
            }
			
			 //hairColor
            if (req.getString("hairColor", "").length() > 0) {
                criteria.addCondition("LOWER(hairColor) = ?").add(String.valueOf(req.getString("hairColor", "").toLowerCase()));
            }
			
			//smoking
            if (req.getString("smoking", "").length() > 0) {
                criteria.addCondition("LOWER(smoking) = ?").add(String.valueOf(req.getString("smoking", "").toLowerCase()));
            }
			
			//education
            if (req.getString("education", "").length() > 0) {
                criteria.addCondition("LOWER(education) = ?").add(String.valueOf(req.getString("education", "").toLowerCase()));
            }
			
            Timestamp dateFrom = DictionaryRecord.parseDate(req.getString("report_registered_from", null), null, Page.getLanguage(req));
            Timestamp dateTo = DictionaryRecord.parseDate(req.getString("report_registered_to", null), null, Page.getLanguage(req));
            //customerRegistrationDate
            if (dateFrom != null) {
                criteria.addCondition("j.regTime >= ?").add(dateFrom);
            }
            //customerRegistrationDate
            if (dateTo != null) {
                criteria.addCondition("j.regTime < ?").add(SqlUtils.calendarAdd(dateTo, 6, 1));
            }
            dateFrom = DictionaryRecord.parseDate(req.getString("report_last_login_from", null), null, Page.getLanguage(req));
            dateTo = DictionaryRecord.parseDate(req.getString("report_last_login_to", null), null, Page.getLanguage(req));
            //customerLastLoginTime
            if (dateFrom != null) {
                criteria.addCondition("j.lastLoginTime >= ?").add(dateFrom);
            }
            //customerLastLoginTime
            if (dateTo != null) {
                criteria.addCondition("j.lastLoginTime < ?").add(SqlUtils.calendarAdd(dateTo, 6, 1));
            }
            //balance
            if (req.getDouble("report_account_balance_from", Double.MAX_VALUE) != Double.MAX_VALUE) {
                criteria.addCondition("j.report_balance>=?").add(req.getDouble("report_account_balance_from", 0.0));
            }
            //balance
            if (req.getDouble("report_account_balance_to", Double.MAX_VALUE) != Double.MAX_VALUE) {
                criteria.addCondition("j.report_balance<=?").add(req.getDouble("report_account_balance_to", 0.0));
            }
            
            //online
            if (req.getBoolean("online", false)) {
            	List<Integer> authorizedCustomerIds = getAuthorizedCustomerIds();
            	if(authorizedCustomerIds.size() > 0){
                	String placeholders = genratePlaceholders(authorizedCustomerIds.size());
                    criteria.addCondition("j.customerId in " + placeholders).add(authorizedCustomerIds.toArray(new String[0]));
            	}
            }
            
           
            final String[] languages = req.getValues("report_language");
            if (languages.length > 0 && languages[0].length() > 0) {
                criteria.addCondition("j.language in (" + StringUtils.repeat("?", ",", languages.length) + ")");
                String[] array;
                for (int length = (array = languages).length, i = 0; i < length; ++i) {
                    final String language = array[i];
                    criteria.add(language);
                }
            }                
            
            criteria.addCondition("(j.status & ?)<>?").add(5).add(5);
            criteria.addCondition("j.id>0");
            
            final DataPager pager = new DataPager(req);
            pager.setDefaultMaxItems(100);
            pager.setSortDefaults(4, 0);
            criteria.setSorting(pager.getSortParams(new String[][] { { "j.id", "" }, { "j.name", "idx_name" }, { "j.email", "idx_email" }, { "j.report_balance", "idx_report_balance" }, { "j.customerScreenName", "idx_screenName" }, { "j.report_totalSpent", "idx_report_totalSpent" }, { "j.regTime", "idx_regTime" }, { "j.lastLoginTime", "idx_lastLoginTime" }, { "j.report_lastPaymentTime", "idx_report_lastPaymentTime" }, { "j.affiliateReference", "" }, { "j.report_lastIP", "" } }));
            if (addToMailer) {
                PersistentBroker.executeQuery("replace into massMailerRecipients(messageId, id, type, name, password, customerScreenName, email, domain) select -1, c.id, 1, c.name, c.password, c.screenName, c.email, c.sessionDomainName from customers c " + criteria.getForceIndex() + ((ip[0] != 0L || ip[1] != 4294967295L) ? "inner join ipReport i on i.customerId=c.id " : ""), criteria);
            }
            else {
                PersistentBroker.setCollectTotalRows(req.getBoolean("collectTotalRows", true));
                final PersistentBroker.ResultList result = CustomerRecord.broker.loadList("select * from (select "
					+ "c.id as customerId,"
					+ "c.name as customerName,"
					+ "c.screenName as customerScreenName,"
					+ "c.email as customerEmail,"
					+ "c.activationCode as customerActivationCode,"
					+ "c.regTime as customerRegistrationDate,"
					+ "c.lastLoginTime as customerLastLoginTime,"
					+ "c.birthDate as birthDate,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'astrological' limit 1) as astrological,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'info' limit 1) as info,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'children' limit 1) as children,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'city' limit 1) as city,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'country' limit 1) as country,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'drink' limit 1) as drink,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'eyeColor' limit 1) as eyeColor,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'hairColor' limit 1) as hairColor,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'gender' limit 1) as gender,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'height' limit 1) as 	height,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'education' limit 1) as education,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'religion' limit 1) as religion,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'time_zone' limit 1) as time_zone,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'smoking' limit 1) as smoking,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'weight' limit 1) as weight,"
					+ "(select cf.value from customerCustomFields cf where cf.customerId=c.id and name = 'online' limit 1) as online, "
					+ "c.status,"
					+ "c.id"
					+ " from customers c) j " //+ criteria.getForceIndex() 
					+ ((ip[0] != 0L || ip[1] != 4294967295L) ? "inner join ipReport i on i.customerId=j.id " : ""), criteria, limitQuery ? pager.getStartIndex() : -1, limitQuery ? pager.getMaxItems() : -1);
                if (!limitQuery) {
                    pager.setMaxItems(result.count);
                }
                pager.initRecords(result.records, result.count);
            }
            if (ip[0] != 0L || ip[1] != 4294967295L) {
                PersistentBroker.executeQuery("drop table ipReport", Criteria.EMPTY);
            }
            SqlUtils.commitTransaction();
            return pager;
        }
        catch (SQLException e) {
            SqlUtils.rollbackTransaction();
            throw new InternalErrorException("", e);
        }
        catch (InternalErrorException e2) {
            SqlUtils.rollbackTransaction();
            throw e2;
        }
    }

	static String genratePlaceholders(int num) {
		if(num == 0) return "()";
		
		String placeholders = "?";
		for(int i = 0; i < num - 1; i++){
			placeholders += ", ?";
		}
		return "(" + placeholders + ")";
	}
    
    private static List<Integer> getAuthorizedCustomerIds() {    	
    	SessionContextIteractiorImpl sessionContextIteractiorImpl = new SessionContextIteractiorImpl();
    	SessionContext.iterateContexts(sessionContextIteractiorImpl);
    	
    	return sessionContextIteractiorImpl.getResult();    	
	}

	@Override
    public void load(final ResultSet rs) throws SQLException, InternalErrorException {
        this.customerId = rs.getInt("customerId");
        this.customerName = rs.getString("customerName");
        this.customerScreenName = rs.getString("customerScreenName");
        this.customerEmail = rs.getString("customerEmail");
        this.customerEmailConfirmed = (rs.getString("customerActivationCode").length() == 0);
        this.customerBirthDate = rs.getTimestamp("birthDate");
        this.customerAstrological = rs.getString("astrological");
        this.customerInfo = rs.getString("info");
        this.customerChildren = rs.getString("children");
        this.customerCity = rs.getString("city");
        this.customerCountry = rs.getString("country");
        this.customerDrink = rs.getString("drink");
        this.customerEyeColor = rs.getString("eyeColor");
		this.customerHairColor = rs.getString("hairColor");
        this.customerGender = rs.getString("gender");
        this.customerHeight = rs.getString("height");
        this.customerReligion = rs.getString("religion");
        this.customerTime_zone = rs.getString("time_zone");
        this.customerSmoking = rs.getString("smoking");
        this.customerTime_zone = rs.getString("time_zone");
        this.customerWeight = rs.getString("weight");
        this.customerOnline = SessionContext.isAuthorized(this.customerId, CustomerPerson.class);            
        
    }
    
    @Override
    public void store(final ResultSet rs) throws InternalErrorException {
        throw new InternalErrorException("Operation not supported");
    }
    
    public interface CustomersIterator{
        void iterate(final CustomerRecord p0);
    }
    
    public static class SessionContextIteractiorImpl implements SessionContext.ContextIterator{
    	private final List<Integer> result;
    	
		public SessionContextIteractiorImpl() {
			this.result = new ArrayList<Integer>();
		}

		public List<Integer> getResult() {
			return this.result;
		}

		@Override
		public void iterate(SessionContext p0) {
			result.add(((CustomerPerson) SessionContext.getAuthorized(p0.getSessionId(), CustomerPerson.class)).getId());
		}
    	
    }
}