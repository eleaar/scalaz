package scalaz

////
/**
 *
 */
////
trait Alternative[F[_]] extends Applicative[F] { self =>
  ////
  def orElse[A](a: F[A], b: => F[A]): F[A]
  
  // derived functions
  def oneOrMore[A](fa: F[A]): F[List[A]] = apF(map(fa)(a => (as: List[A]) => a :: as))(zeroOrMore(fa))
  
  def zeroOrMore[A](fa: F[A]): F[List[A]] = orElse(oneOrMore(fa), point(List()))

  trait AlternativeLaw {
    def associative[A](f1: F[A], f2: F[A], f3: F[A])(implicit FA: Equal[F[A]]): Boolean =
      FA.equal(orElse(f1, orElse(f2, f3)), orElse(orElse(f1, f2), f3))
  }
  def alternativeLaw = new AlternativeLaw {}
  ////
  val alternativeSyntax = new scalaz.syntax.AlternativeSyntax[F] {}
}

object Alternative {
  @inline def apply[F[_]](implicit F: Alternative[F]): Alternative[F] = F

  ////

  ////
}

