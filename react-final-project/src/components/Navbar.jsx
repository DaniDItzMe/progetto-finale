import api from "../axios";
import { useEffect, useState } from "react";
import { Link, NavLink } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useLoginContext } from "../contexts/LoginContext";

export function Navbar() {
  const navigate = useNavigate();

  const { logged, setLogged, auth, setAuth, setLoading } = useLoginContext();
  const server = import.meta.env.VITE_SERVER_ROOT;

  useEffect(() => {
    console.log("WAITING FOR AUTHENTICATION");
    setLoading(true);

    api
      .get(`/api/auth`, { withCredentials: true })
      .then((response) => {
        console.log(response.data);

        setAuth(response.data);
        setLoading(false);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [logged]);

  const handleLogout = (e) => {
    e.preventDefault();

    api.post(`/logout`, null, { withCredentials: true }).then((response) => {
      console.log(response);
      setLogged(false);
      navigate("/logout");
    });
  };

  return (
    <>
      <nav
        className="navbar navbar-expand-lg bg-body-tertiary "
        data-bs-theme="dark"
      >
        <div className="container-fluid">
          <NavLink className="navbar-brand fw-bold fs-2" to={"/"}>
            Checkpoint Store
          </NavLink>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <NavLink className="nav-link" aria-current="page" to="/" end>
                  Home
                </NavLink>
              </li>
              {auth?.role == "ADMIN" && (
                <li className="nav-item">
                  <NavLink
                    className="nav-link"
                    aria-current="page"
                    to="/genres"
                    end
                  >
                    Genres
                  </NavLink>
                </li>
              )}
              <li className="nav-item">
                <NavLink
                  className="nav-link"
                  aria-current="page"
                  to="/consoles"
                  end
                >
                  Consoles
                </NavLink>
              </li>
            </ul>

            <form action="/" className="d-flex" role="search">
              <input
                className="form-control me-2"
                name="search"
                type="search"
                placeholder="Search by game name"
                aria-label="Search"
              />
              <button className="btn btn-outline-success" type="submit">
                Search
              </button>
            </form>

            {auth.authenticated && (
              <button
                onClick={(e) => {
                  handleLogout(e);
                  setLogged(true);
                }}
                className="btn btn-danger ms-3"
              >
                Logout
              </button>
            )}

            {!auth.authenticated && (
              <button
                className="btn btn-success ms-3"
                onClick={() => navigate("/login")}
              >
                Login
              </button>
            )}
          </div>
        </div>
      </nav>
    </>
  );
}
