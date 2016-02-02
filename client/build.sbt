name := """redisClient"""

version := "1.0"

organization := "com.github.uryyyyyyy"

crossPaths := false

autoScalaLibrary := false

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"


libraryDependencies ++= Seq(
	"redis.clients" % "jedis" % "2.8.0",
	"biz.paluch.redis" % "lettuce" % "4.1.Final",
	"org.redisson" % "redisson" % "2.2.6",
	"junit" % "junit" % "4.12" % "test"
)
