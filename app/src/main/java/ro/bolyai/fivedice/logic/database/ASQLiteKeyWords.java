package ro.bolyai.fivedice.logic.database;

/**
 * //TODO Copy this class from this project into yours
 * //TODO mark it the left project tree Ctrl + C
 * //TODO mark your database package Ctrl  + V
 * //TODO Submit the Dialog
 * This class implements all needed Keywords
 * for creating SQL-Statements. The Keywords
 * are correct and teste. So every DatabaseClass
 * has to use this class, so there are no typos
 * in statemtents it self.
 *
 */
public abstract class ASQLiteKeyWords {
	//region Datatypes
	protected static final String DATA_TYPE_INTEGER           = " INTEGER ";
	protected static final String DATA_TYPE_INTEGER_INC_COMMA = " INTEGER, ";
	
	protected static final String DATA_TYPE_REAL           = " REAL ";
	protected static final String DATA_TYPE_REAL_INC_COMMA = " REAL, ";
	
	protected static final String DATA_TYPE_TEXT_INC_COMMA = " TEXT, ";
	protected static final String DATA_TYPE_TEXT           = " TEXT ";
	//endregion
	
	//region table actions
	protected static final String CREATE_TBL            = "CREATE TABLE ";
	protected static final String SELECT_TBL            = "SELECT * FROM ";
	protected static final String SELECT_COUNT_FROM_TBL = "SELECT COUNT(*) FROM ";
	protected static final String DROP_TBL              = "DROP TABLE IF EXISTS ";
	protected static final String UPDATE_TBL            = "UPDATE ";
	protected static final String DELETE_FROM_TBL       = "DELETE FROM ";
	//endregion
	
	//region keys
	protected static final String PRIMARY_KEY_AUTO_INCREMENT_INC_COMMA = DATA_TYPE_INTEGER + "PRIMARY KEY AUTOINCREMENT, ";
	protected static final String PRIMARY_KEY_INC_COMMA                = DATA_TYPE_INTEGER + "PRIMARY KEY, ";
	//endregion
	
	//region operators
	protected static final String SET_OPERATOR                     = " SET ";
	protected static final String AND_OPERATOR                     = " AND ";
	protected static final String EQUALS_OPERATOR_INC_PLACE_HOLDER = " =?";
	//endregion
	
	//region conditions
	protected static final String WHERE_CONDITION = " WHERE ";
	
	//endregion
	
	//region chars
	protected static final String CHAR_COMMA                   = ", ";
	protected static final String CHAR_OPEN_BRACKET            = "(";
	protected static final String CHAR_CLOSE_BRACKET_SEMICOLON = ");";
	//endregion
}
