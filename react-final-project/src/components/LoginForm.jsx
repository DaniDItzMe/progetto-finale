import api from "../axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLoginContext } from "../contexts/LoginContext";
export function LoginForm() {
  const [loginError, setLoginError] = useState("");

  const { logged, setLogged } = useLoginContext();
  const server = import.meta.env.VITE_SERVER_ROOT;

  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleFormData = (e) => {
    setFormData((c) => {
      return {
        ...c,
        [e.target.name]: e.target.value,
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = new URLSearchParams();
    data.append("username", formData.username);
    data.append("password", formData.password);

    try {
      await api.post(`/login`, data, {
        withCredentials: true,
      });
      setLogged(true);
      navigate("/");
    } catch (err) {
      if (err.status === 502) {
        setLoginError("SERVER UNAVAILABLE");
        return;
      }
      console.log(err.response);
      setLoginError(err.response.data.error);
    }
  };

  return (
    <>
      <div className="d-flex justify-content-center mt-5">
        <form
          method="post"
          className="w-25 border border-2 border-dark rounded p-3"
          onSubmit={handleSubmit}
        >
          {loginError && (
            <div
              className="alert alert-warning alert-dismissible fade show d-flex align-items-center justify-content-center"
              role="alert"
            >
              <i className="bi bi-exclamation-triangle-fill me-2"></i>

              <div className="fw-bold">{loginError}</div>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="alert"
                aria-label="Close"
                onClick={() => setLoginError("")}
              ></button>
            </div>
          )}

          <div className="mb-3">
            <label htmlFor="username" className="form-label fw-bold">
              Username
            </label>
            <input
              type="text"
              className="form-control"
              placeholder="username"
              name="username"
              id="username"
              value={formData.username}
              onChange={handleFormData}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="password" className="form-label fw-bold">
              Password
            </label>
            <input
              type="password"
              className="form-control"
              placeholder="password"
              name="password"
              id="password"
              value={formData.password}
              onChange={handleFormData}
            />
          </div>

          <div className="w-100">
            <button className="btn btn-dark w-100 fw-bold" type="submit">
              Login
            </button>
          </div>
        </form>
      </div>
    </>
  );
}
