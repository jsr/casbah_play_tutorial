package controllers;

import play.mvc._;
import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters._

object Messages extends Controller { 

	val mongoConn = MongoConnection()

	def index = { 

		val msgs = mongoConn("casbah_test")("test_data").find( "msg" $exists true $ne "" ) 
		val msgStrings = msgs.map( (obj: DBObject) => obj.getOrElse("msg","") )
		Template( 'msgStrings -> msgStrings.asJava )
	}

	def save(msg:String) = { 
		val doc = MongoDBObject("msg" -> msg)
		mongoConn("casbah_test")("test_data").save( doc )
		Redirect("/")
	}
}
