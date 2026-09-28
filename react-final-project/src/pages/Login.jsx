import { LoginForm } from "../components/LoginForm";
import { useLocation } from "react-router-dom";

export function Login() {
  const location = useLocation();

  return (
    <>
      <h1 className="text-center">Login</h1>
      {location.pathname == "/logout" && (
        <div className="d-flex justify-content-center mt-5">
          <div
            className="alert w-25 alert-warning d-flex align-items-center justify-content-center"
            role="alert"
          >
            <i className="bi bi-exclamation-triangle-fill me-2"></i>

            <div className="fw-bold">You have been signed out</div>
          </div>
        </div>
      )}

      <LoginForm></LoginForm>
    </>
  );
}
