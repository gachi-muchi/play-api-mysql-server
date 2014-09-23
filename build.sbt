name := "play-app-mysql-server"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.google.inject" % "guice" % "3.0",
  "mysql" % "mysql-connector-java" % "5.1.32",
  "com.googlecode.xmemcached" % "xmemcached" % "2.0.0"
)

play.Project.playJavaSettings
