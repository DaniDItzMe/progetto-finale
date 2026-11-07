import style from "./css/table.module.css";
import { useNavigate } from "react-router-dom";

export function Table({ headers, list, type }) {
  const navigate = useNavigate();

  const server = import.meta.env.VITE_SERVER_ROOT;

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
          </tr>
        </thead>
        <tbody>
          {list?.map((el) => (
            <tr
              key={el.id}
              className={`${type == "games" || type == "consoles" ? style.gameCard : ""} text-center`}
              onClick={() => {
                if (type == "games") {
                  navigate(`/show/${el.id}`);
                } else if (type == "consoles") {
                  navigate(`/consoles/show/${el.id}?console=${el.name}`);
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
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
