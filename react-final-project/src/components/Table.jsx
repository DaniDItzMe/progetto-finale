import { Link } from "react-router-dom";
import style from "./css/table.module.css";
import { useNavigate } from "react-router-dom";
import api from "../axios";
import { useLoginContext } from "../contexts/LoginContext";

export function Table({ headers, list, type }) {
  const navigate = useNavigate();

  const { auth } = useLoginContext();
  const server = import.meta.env.VITE_SERVER_ROOT;

  const handleDelete = async (e, id) => {
    e.preventDefault();
    e.stopPropagation();

    const response = await api.delete(
      type == "games"
        ? `/api/games/delete/${id}`
        : type == "genres"
          ? `/api/genres/delete/${id}`
          : `/api/consoles/delete/${id}`,
    );
    console.log(response);

    if (response.status === 200) {
      location.reload();
      return;
    } else {
      console.warn("ERROR");
    }
  };

  return (
    <>
      <table className="table">
        <thead>
          <tr className="text-center">
            {headers.map((el, index) => (
              <th key={index} scope="col">
                {el}
              </th>
            ))}

            <th></th>
          </tr>
        </thead>
        <tbody>
          {list?.map((el) => (
            <tr
              key={el.id}
              className={`${type == "games" || "consoles" ? style.gameCard : ""} text-center`}
              onClick={() => {
                if (type == "games") {
                  navigate(`/show/${el.id}`);
                } else if (type == "consoles") {
                  navigate(`/consoles/show/${el.id}`);
                }
              }}
            >
              {type == "games" && (
                <>
                  <td className="align-middle">
                    <img
                      className="img-fluid"
                      style={{
                        width: "300px",
                        height: "150px",
                        objectFit: "contain",
                      }}
                      src={`${server}api/games/image/` + el.id}
                      alt={el.name}
                    />
                  </td>

                  <td className="align-middle">{el?.name}</td>
                  <td className="align-middle">{el?.description}</td>
                  <td className="align-middle fw-bold">{el?.price}€</td>
                </>
              )}

              {type == "genres" && (
                <>
                  <td className="align-middle">{el?.id}</td>
                  <td className="align-middle">{el?.name}</td>
                </>
              )}

              {type == "consoles" && (
                <>
                  <td className="align-middle">{el?.id}</td>
                  <td className="align-middle fw-bold">{el?.name}</td>
                  <td className="align-middle">{el?.description}</td>
                  <td className="align-middle fw-bold">{el?.price}€</td>
                </>
              )}

              {auth.role == "ADMIN" && (
                <>
                  <td className="align-middle">
                    <Link
                      className="btn btn-primary"
                      to={
                        type == "games"
                          ? "/edit/" + el.id
                          : type == "genres"
                            ? "/genres/edit/" + el.id
                            : "/consoles/edit/" + el.id
                      }
                      onClick={(e) => {
                        e.stopPropagation();
                      }}
                    >
                      Edit
                    </Link>
                  </td>
                  <td className="align-middle">
                    <button
                      className="btn btn-danger"
                      data-bs-toggle="modal"
                      data-bs-target={"#delete-modal-" + el.id}
                      onClick={(e) => {
                        e.stopPropagation();
                      }}
                    >
                      Delete
                    </button>
                    <div
                      className="modal fade"
                      id={"delete-modal-" + el.id}
                      tabIndex="-1"
                      aria-labelledby="delete-modal"
                      aria-hidden="true"
                    >
                      <div className="modal-dialog modal-dialog-centered">
                        <div className="modal-content">
                          <div className="modal-header">
                            <h1
                              className="modal-title fs-5"
                              id="exampleModalLabel"
                            >
                              Delete {el.name}
                            </h1>
                            <button
                              type="button"
                              className="btn-close"
                              data-bs-dismiss="modal"
                              aria-label="Close"
                            ></button>
                          </div>
                          <div className="modal-body">
                            Are you sure you want to delete {el.name}?
                          </div>
                          <div className="modal-footer">
                            <button
                              type="button"
                              className="btn btn-secondary"
                              data-bs-dismiss="modal"
                            >
                              Close
                            </button>
                            <form onSubmit={(e) => handleDelete(e, el.id)}>
                              <button
                                type="submit"
                                className="btn btn-danger"
                                data-bs-dismiss="modal"
                                onClick={(e) => e.stopPropagation()}
                              >
                                Delete
                              </button>
                            </form>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
