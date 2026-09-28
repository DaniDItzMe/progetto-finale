import { Link } from "react-router-dom";

export function NotFound() {
  return (
    <>
      <div className="d-flex justify-content-center flex-column align-items-center gap-3">
        <h1 className="text-center fw-bold text-danger">PAGE NOT FOUND!</h1>
        <Link className="btn btn-dark w-25" to={"/"}>
          Home
        </Link>
      </div>
    </>
  );
}
