package com.maocq.freemonad

object FreeM extends App {

  type UserName = String
  type Image = String

  case class User( name: UserName, photo: Image )


  sealed trait Service[A]
  // Caso de negocio
  case class GetUserName(userId: Long) extends Service[UserName]
  case class GetUserPhoto(userId: Long) extends Service[Image]


  import cats.free.Free

  type ServiceF[A] =  Free[Service, A]


  import cats.free.Free.liftF


  // Eleva un F[A] en la mónada libre.
  def getUserName(userId: Long): Free[Service, UserName] = liftF( GetUserName( userId ) )
  def getUserPhoto(userId: Long): ServiceF[Image] = liftF( GetUserPhoto( userId ) )


  def getUser(id: Long) = for {
    n <- getUserName( id )
    p <- getUserPhoto( id )
  } yield User( n, p )



 //Nuestro primer intérprete ************************

  //Un intérprete requiere una mónada como parte final de la transformación
  // ~> natural transformation
  import cats.{Id, ~>}

  object ServicePrinter extends (Service ~> Id) {
    def apply[A](fa: Service[A]): Id[A] = fa match {

      case GetUserName(userId) =>
        println(s"GetUserName $userId...")
        "Mauricio"

      case GetUserPhoto(userId) =>
        println(s"GetUserPhoto...")
        " (°~°) "

    }
  }

  val sp: Id[User] = getUser( 1 ).foldMap( ServicePrinter )

  println( sp )


  // Un aspecto importante de foldMap es su apilamiento de seguridad.
  // Se evalúa cada paso de cálculo en la pila y después se apila y se reinicia.
  // Este proceso se conoce como trampolining.


}
