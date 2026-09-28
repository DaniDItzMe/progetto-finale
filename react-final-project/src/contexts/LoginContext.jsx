import { useContext } from "react";
import { useState } from "react";
import { createContext } from "react";

const LoginContext = createContext();

function LoginProvider({ children }) {
  const [logged, setLogged] = useState(false);
  const [auth, setAuth] = useState({});
  const [loading, setLoading] = useState(true);

  return (
    <LoginContext.Provider
      value={{
        logged,
        setLogged,
        auth,
        setAuth,
        loading,
        setLoading,
      }}
    >
      {children}
    </LoginContext.Provider>
  );
}

function useLoginContext() {
  const context = useContext(LoginContext);
  return context;
}

export { LoginProvider, useLoginContext };
