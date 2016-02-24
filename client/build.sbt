name := """redisClient"""

version := "1.0"

organization := "com.github.uryyyyyyy"

crossPaths := false

autoScalaLibrary := false

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

enablePlugins(JavaAppPackaging)

javacOptions in Compile ++= Seq("-source", "1.8",  "-target", "1.8") 
javacOptions in doc ++= Seq("-source", "1.8") 

libraryDependencies ++= Seq(
	"redis.clients" % "jedis" % "2.8.0",
	"biz.paluch.redis" % "lettuce" % "4.1.Final",
	"org.redisson" % "redisson" % "2.2.6",
	"junit" % "junit" % "4.12" % "test"
)
