import { Link } from "react-router-dom";

export function NotFound() {
  return (
    <>
      <div className="text-center">
        <h1 className="text-danger fw-bold mb-3">ERROR 404 NOT FOUND</h1>

        <h3 className="mb-3">Pagina non trovata</h3>

        <Link className="btn btn-secondary" to="/">
          Torna alla home
        </Link>
      </div>
    </>
  );
}
