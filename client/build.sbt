name := """redisClient"""

version := "1.0"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
	"redis.clients" % "jedis" % "2.8.0",
	"biz.paluch.redis" % "lettuce" % "4.1-SNAPSHOT",
	"org.redisson" % "redisson" % "2.2.5"
)
